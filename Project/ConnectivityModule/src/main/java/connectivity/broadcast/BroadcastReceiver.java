package connectivity.broadcast;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class implements a UDP protocol transfer in order to receive and find what addresses are reachable from the current
 * socket
 */
public class BroadcastReceiver extends Thread {
    /**
     * The socket which is receiving the data
     */
    private final DatagramSocket socket;
    /**
     * The set of addresses that are reachable from a socket
     */
    private final Set<InetAddress> addresses;
    /**
     * The set of addresses to be ignored whether they are or not reachable
     */
    private final Set<InetAddress> toIgnore;
    /**
     * The time limit in which the current socket can receive connection
     */
    private final int timeout;

    /**
     * The constructor initializes the timeout, set a port on which the connection with the socket to be made and set a list
     * of addresses that need to be ignored
     * @param port The port on which the connection is made
     * @param timeout The time limit for receiving new connections
     * @param toIgnore A set of addresses to be ignored
     * @throws UnknownHostException
     * @throws SocketException
     */
    BroadcastReceiver(int port, int timeout, Set<InetAddress> toIgnore) throws UnknownHostException, SocketException {
        this.timeout = timeout;
        this.addresses = new HashSet<>();
        this.socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
        this.toIgnore = toIgnore;
    }

    /**
     * This method describes what will happen when the BroadcastReceiver's start() method is called. The socket will wait
     * for new possible connections, within a certain time limit called timeout, and adding those addresses to a set of
     * addresses that are reachable
     */
    @Override
    public void run() {
        byte[] myIPv4;
        try {
            //socket.setSoTimeout(2000);
            myIPv4 = InetAddress.getLocalHost().getAddress();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            return;
        }

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
                    String str = new String(p.getData(), 0, p.getLength());
                } catch (IOException ignored) {
                }
            }
    }

    /**
     * The method is used to return all the addresses that are reachable from a socket
     * @return A set of addresses
     */
    public Set<InetAddress> getAddresses() {
        return addresses;
    }
}
