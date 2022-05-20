import Connectivity.Peer;
import Exceptions.PeerDisconnectedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestConnectivityReceiver {
    public static void main(String[] args) throws IOException, PeerDisconnectedException, InterruptedException {
        Peer peer = new Peer(7337);
        System.out.println("Your ip is: " + peer.getIP());
        var localIpList = peer.getDevices();
        System.out.println(localIpList);

        System.out.println(ReceiverTest.class.getClassLoader().getResource("./").getPath() + "test.txt");
        File file = new File(ReceiverTest.class.getClassLoader().getResource("./").getPath() + "test.txt");

        FileOutputStream fileOut = new FileOutputStream(file);
        while (true) { // Waiting for the server to receive the connection
            peer.checkActiveConnections();
            if (peer.get("192.168.56.1") != null) {
                peer.get("192.168.56.1").receiveFile(fileOut);
                break;
            }
        }
    }
}