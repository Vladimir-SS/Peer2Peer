import Connectivity.Peer;

import java.io.IOException;

public class ConnectivityTest1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Peer peer = new Peer(4334);
        var localIpList = peer.getDevices();
        System.out.println(localIpList);
        peer.connectDevice(4,4334);
    }
}
