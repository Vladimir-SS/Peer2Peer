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
            peer.connectDevice("192.168.100.9",4444);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}