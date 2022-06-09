package connectivity.broadcast;

import connectivity.exceptions.BroadcastFailedException;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BroadcastReceiver implements Runnable {
    private final DatagramSocket socket;
    private final Set<InetAddress> addresses;
    private final Set<InetAddress> toIgnore;

    private final int timeout;


    BroadcastReceiver(int port, int timeout, Set<InetAddress> toIgnore) throws BroadcastFailedException {
        this.timeout = timeout;
        this.addresses = new HashSet<>();
        try {
            this.socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
        } catch (IOException e) {
            throw new BroadcastFailedException(e);
        }
        this.toIgnore = toIgnore;
    }

    @Override
    public void run() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> {socket.close();executor.shutdown();}, timeout, TimeUnit.SECONDS);

        byte[] bytes = new byte[1024];
        DatagramPacket p = new DatagramPacket(bytes, bytes.length);

            while (!socket.isClosed()){
                try {
                    socket.receive(p);
                    var inetAddress = p.getAddress();
                    if(!toIgnore.contains(inetAddress)) {
                        addresses.add(inetAddress);
                        System.out.println(inetAddress.getHostAddress() + " is reachable: ");
                    }
                } catch (IOException ignored) {
                }
            }
    }

    public Set<InetAddress> getAddresses() {
        return addresses;
    }
}
