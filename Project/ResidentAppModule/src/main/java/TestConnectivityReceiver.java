import Connectivity.Peer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestConnectivityReceiver {
    public static void main(String[] args) throws IOException {
        Peer peer = new Peer(4444);
        System.out.println("Waiting for Connection...");
        //peer.waitingConnection();
        System.out.println("Waiting for Info...");
        System.out.println(TestConnectivityReceiver.class.getClassLoader().getResource("./").getPath() + "test.txt");
        File file = new File(TestConnectivityReceiver.class.getClassLoader().getResource("./").getPath() + "test.txt");
        FileOutputStream fileOut = new FileOutputStream(file);
        while(true) { // Waiting for the server to receive the connection
            //peer.checkActiveConnections();
            if (peer.get("10.20.0.14") != null) {
                peer.get("10.20.0.14").receiveFile(fileOut);
                break;
            }
        }
    }
}
//comentariu