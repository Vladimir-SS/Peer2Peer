package connectivity.broadcast;

import java.io.Closeable;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * This is the class that performs a broadcast in our local network
 */
public class Broadcast implements Closeable {
    /**
     * This is the variable used to send the message through broadcast
     */
    private final BroadcastSender sender;
    /**
     * This executor is used send a message through broadcast at a fixed period of time
     */
    private final ScheduledExecutorService executor;
    /**
     * This represents the port on which the whole broadcast will be realised
     */
    private final int port;

    /**
     * The constructor initializes the port with a given number and set the period of time for which the messages will
     * be sent. It is also getting al the possible addresses of our local network and starts to send to them a message
     * at fixed period of time
     * @param port A port number
     * @param period A period of time in seconds
     * @throws SocketException
     */
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

    /**
     * This method receive a networkInterface and returns either an address interface of that network interface
     * of which the broadcast address is not null, or null
     * @param networkInterface A given network interface
     * @return An address interface that has a broadcast address, or null
     */
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

    /**
     * This method search through all active network interfaces and creates a set list of all interface addresses of that
     * network interfaces that have an available broadcast address
     * @return A set of all interface addresses of a user that have an available broadcast address
     */
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

    /**
     * This method returns a set of addresses that can be reached from the client who is calling this method
     * @param timeout A fixed amount of time in which a client can receive broadcast messages
     * @param async This variable set if the receiving of data is done asynchronous or not
     * @return A set of addresses that are reachable from a client
     * @throws SocketException
     * @throws UnknownHostException
     */
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

    /**
     * This method is used to auto close the sender variable and the executor variable
     */
    @Override
    public void close() {
        sender.close();
        executor.shutdown();
    }

}