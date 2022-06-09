package connectivity.broadcast;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * This class implements a UDP protocol transfer in order to send to a list of addresses a message
 */
public class BroadcastSender implements Runnable, Closeable {
    /**
     * The socket which is used to send the data
     */
    private final DatagramSocket sender;
    /**
     * A list of datagram packages which are sent through socket
     */
    private final List<DatagramPacket> packages;

    /**
     * The constructor initializes the sender and the list of packages, setting for each package the address and the port
     * together with the message that will be sent
     * @param port The port on which the connections are made
     * @param sendTo A list of addresses that are being used to send a message
     * @throws SocketException
     */
    BroadcastSender(int port, List<InetAddress> sendTo) throws SocketException {
        this.sender = new DatagramSocket();
        this.sender.setBroadcast(true);
        byte[] message = new byte[0];
        this.packages = sendTo
                .stream()
                .map(inetAddress -> new DatagramPacket(message, 0, inetAddress, port))
                .toList();
    }

    /**
     * This method describes what will happen when the BroadcastSender's start() method is called. Every package of the
     * list of packages is sent to a certain socket destination
     */
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

    /**
     * This method is used to auto close the datagram socket variable, sender
     */
    @Override
    public void close() {
        sender.close();
    }
}
