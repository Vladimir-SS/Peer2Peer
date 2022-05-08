import Connectivity.Peer;
import Exceptions.PeerDisconnectedException;

import java.io.IOException;
import java.net.URL;

public class SenderTest {

    public static void main(String[] args) {
        try {
            System.out.println("Making Peer...");
            Peer peer = new Peer(7337);
            var localIpList = peer.getDevices();
            System.out.println(localIpList);
            System.out.println("Connecting...");
            peer.connectDevice("10.100.58.217",4444);
            System.out.println("Sending...");
            URL resource = SenderTest.class.getClassLoader().getResource("message.txt");
            while(true) {   // Waiting for a receiver to accept the connection
                //peer.checkActiveConnections();
                if(peer.get("10.100.58.217") != null) {
                    peer.get("10.100.58.217").sendFile(resource.getPath());
                    break;
                }
            }
        } catch (IOException | InterruptedException | PeerDisconnectedException e) {
            e.printStackTrace();
        }

    }
}