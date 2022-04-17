import Connectivity.Peer;

import java.io.IOException;

public class ConnectivityTest2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Peer peer = new Peer(5000);
        var localIpList = peer.getDevices();
        System.out.println(localIpList);
        peer.connectDevice(4,5000);
    }
}
