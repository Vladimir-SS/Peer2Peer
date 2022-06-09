package george;

import connectivity.Peer;
import george.resident.SynchronizedDirectory;
import george.resident.sync.ConnectivityResident;

import java.io.IOException;
import java.nio.file.Paths;

public class TestingB {
    public static void main(String[] args) throws IOException {
        Peer peer = new Peer(2222);
        SynchronizedDirectory sd = new SynchronizedDirectory(Paths.get("C:\\Users\\georg\\Desktop\\dir1"));

        ConnectivityResident cr = new ConnectivityResident(peer, sd);
        cr.start();
    }
}
