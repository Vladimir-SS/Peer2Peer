import Connectivity.Peer;
import java.io.IOException;

public class TestConnectivityReceiver {
    public static void main(String[] args) throws IOException {
        Peer peer = new Peer(4444);
        System.out.println("Waiting for Connection...");
        peer.waitingConnection();
        System.out.println("Connected");
    }
}