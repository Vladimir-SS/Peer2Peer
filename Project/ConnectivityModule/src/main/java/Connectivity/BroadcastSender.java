package Connectivity;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;

public class BroadcastSender implements Runnable, Closeable {
    private final DatagramSocket sender;
    private final DatagramPacket packet;


    private static InetAddress getBroadcastIP() throws UnknownHostException, SocketException {
        InetAddress localHost = Inet4Address.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

        var rv = networkInterface.getInterfaceAddresses().get(0).getBroadcast();
        System.out.println(rv);
        return rv;
    }

    BroadcastSender(int port) throws SocketException, UnknownHostException {
        sender = new DatagramSocket();
        sender.setBroadcast(true);

        byte[] message = new byte[0];

        System.out.println(InetAddress.getLocalHost().getHostAddress());
        packet = new DatagramPacket(message, 0, getBroadcastIP(), port);
    }

    @Override
    public void run() {
        try {
            sender.send(packet);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        sender.close();
    }
}
