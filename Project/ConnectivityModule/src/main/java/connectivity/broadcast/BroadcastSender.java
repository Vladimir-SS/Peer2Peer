package connectivity.broadcast;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.List;

public class BroadcastSender implements Runnable, Closeable {
    private final DatagramSocket sender;
    private final List<DatagramPacket> packages;

    BroadcastSender(int port, List<InetAddress> sendTo) throws SocketException {
        this.sender = new DatagramSocket();
        this.sender.setBroadcast(true);
        byte[] message = new byte[0];
        this.packages = sendTo
                .stream()
                .map(inetAddress -> new DatagramPacket(message, 0, inetAddress, port))
                .toList();
    }


    @Override
    public void run() {
        packages.forEach(packet -> {
            try {
                sender.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void close() {
        sender.close();
    }
}
