import Connectivity.Peer;

import java.io.IOException;

public class SenderTest {

    public static void main(String[] args) {
        try {
            System.out.println("Making Peer...");
            Peer peer = new Peer(7337);
            System.out.println("Connecting...");
            peer.connectDevice(130);
            System.out.println("Sending...");
            peer.get(0).sendFile("123");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
