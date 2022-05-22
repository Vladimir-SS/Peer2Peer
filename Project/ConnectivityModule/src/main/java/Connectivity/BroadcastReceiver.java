package Connectivity;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*default*/ class BroadcastReceiver extends Thread {
    private final DatagramSocket socket;
    private final Set<InetAddress> addresses;
    private final Set<InetAddress> toIgnore;

    private final int timeout;


    BroadcastReceiver(int port, int timeout, Set<InetAddress> toIgnore) throws UnknownHostException, SocketException {
        this.timeout = timeout;
        this.addresses = new HashSet<>();
        this.socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
        this.toIgnore = toIgnore;
    }

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
                } catch (IOException e) {
                }
            }
    }

    public Set<InetAddress> getAddresses() {
        return addresses;
    }
}
