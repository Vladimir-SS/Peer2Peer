import Connectivity.Peer;
import java.io.IOException;

public class TestConnectivitySender {

    public static void main(String[] args) {
        try {
            System.out.println("Making Peer...");
            Peer peer = new Peer(7337);
            System.out.println("Connecting...");
            peer.connectDevice(9,4444);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
