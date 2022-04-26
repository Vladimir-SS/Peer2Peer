import Connectivity.Peer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReceiverTest {
    public static void main(String[] args) throws IOException {
        Peer peer = new Peer(4444);
        System.out.println("Waiting for Connection...");
        peer.waitingConnection();
        System.out.println("Waiting for Info...");
        File file = new File(ReceiverTest.class.getResource("/").getPath() + "/test.txt");
        FileOutputStream fileOut = new FileOutputStream(file);
        peer.get(0).receiveFile(fileOut);
    }
}