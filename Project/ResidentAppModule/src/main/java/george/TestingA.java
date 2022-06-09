package george;

import connectivity.Peer;
import connectivity.connection.Connection;
import george.resident.SynchronizedDirectory;
import george.resident.sync.ConnectivityResident;

import java.io.*;
import java.net.Inet4Address;
import java.nio.file.Paths;

public class TestingA {

    public static void main(String[] args) throws IOException {
        //George: by hand so I don't have to write commands EVERY 20 SEC
        Peer peer = new Peer(2222);
        peer.connectDevice(Inet4Address.getByName("192.168.1.130"));
        SynchronizedDirectory sd = new SynchronizedDirectory(Paths.get("C:\\Users\\georg\\Downloads\\Integration\\whatif\\IPProjectB2"));

        ConnectivityResident cr = new ConnectivityResident(peer, sd);
        cr.start();

        Connection connection = cr.getConnectedDevices().get(0);
        cr.syncFiles(connection);
    }
}
