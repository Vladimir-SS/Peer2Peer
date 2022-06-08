package connectivity.broadcast;

import java.io.Closeable;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 
 */
public class Broadcast implements Closeable {
    private final BroadcastSender sender;
    private final ScheduledExecutorService executor;
    private final int port;

    public Broadcast(int port, int period) throws SocketException {
        this.port = port;

        var broadcastAddresses =
                getAvailableInterfaces().stream()
                        .map(InterfaceAddress::getBroadcast)
                        .toList();

        sender = new BroadcastSender(port, broadcastAddresses);

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(sender, 0, period, TimeUnit.SECONDS);
    }

    private static InterfaceAddress getAvailableAddress(NetworkInterface networkInterface){

        try {
            if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual())
                return null;

            var interfaceAddresses = networkInterface.getInterfaceAddresses();

            for (var address :
                    interfaceAddresses) {
                if (address.getBroadcast() != null)
                    return address;
            }

        } catch (SocketException ignored) {
        }

        return null;
    }

    private static Set<InterfaceAddress> getAvailableInterfaces() {

        Set<InterfaceAddress> interfaces = new HashSet<>();

        try {

            for (Enumeration<NetworkInterface> interfaceEnumeration =
                 NetworkInterface.getNetworkInterfaces();
                 interfaceEnumeration.hasMoreElements(); ) {

                NetworkInterface networkInterface = interfaceEnumeration.nextElement();
                InterfaceAddress availableAddress = getAvailableAddress(networkInterface);

                if(availableAddress != null)
                    interfaces.add(availableAddress);

            }

        } catch (Exception ignored) {
        }

        return interfaces;
    }


    public Set<InetAddress> getAddresses(int timeout, boolean async) throws SocketException, UnknownHostException {
        var ignoredAddresses =
                getAvailableInterfaces().stream()
                        .map(InterfaceAddress::getAddress)
                        .collect(Collectors.toSet());

        BroadcastReceiver receiver = new BroadcastReceiver(port, timeout, ignoredAddresses);

        if(async)
            receiver.start();
        else
            receiver.run();

        return receiver.getAddresses();
    }

    @Override
    public void close() {
        sender.close();
        executor.shutdown();
    }

}