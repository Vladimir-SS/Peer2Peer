import Connectivity.Peer;

import java.io.IOException;
import java.net.URL;

public class TestConnectivitySender {

    public static void main(String[] args) {
        try {
            System.out.println("Start...");
            Peer peer = new Peer(7337);
            var localIpList = peer.getDevices();
            System.out.println(localIpList);
            System.out.println("Connecting...");
            peer.connectDevice("169.254.222.108",4444);
            System.out.println("Sending...");
            URL resource = TestConnectivitySender.class.getClassLoader().getResource("message.txt");
            while(true) {   // Waiting for a receiver to accept the connection
                //peer.checkActiveConnections();
                if(peer.get("169.254.222.108") != null) {
                    peer.get("169.254.222.108").sendFile(resource.getPath());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}