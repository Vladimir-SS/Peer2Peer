import Connectivity.Peer;

import java.io.IOException;

public class ReceiverTest {
    public static void main(String[] args) throws IOException {
        Peer peer = new Peer(7337);

        System.out.println("Waiting for Connection...");
        peer.waitingConnection();
        System.out.println("Waiting for Info...");
        peer.incomingFile();
        System.out.println("Done");
    }
}
