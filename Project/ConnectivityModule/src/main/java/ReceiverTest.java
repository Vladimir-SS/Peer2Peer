import Connectivity.Peer;
import Connectivity.Port;
import Exceptions.PeerDisconnectedException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ReceiverTest {
    public static void main(String[] args) throws IOException, PeerDisconnectedException, InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("*** To start a connection enter a port ***:");
        int port = Integer.parseInt(input.nextLine());

        while(!Port.isAvailable(port)) {
            System.out.println("The port " + port + " is already used. Enter a new port: ");
            port = Integer.parseInt(input.nextLine());
        }

        Peer peer = new Peer(port); // 7337
        System.out.println("Your ip is: " + peer.getIP());
        var localIpList = peer.getDevices();
        System.out.println(localIpList);

        System.out.println(ReceiverTest.class.getClassLoader().getResource("./").getPath() + "test.txt");
        File file = new File(ReceiverTest.class.getClassLoader().getResource("./").getPath() + "test.txt");

        FileOutputStream fileOut = new FileOutputStream(file);
        while(true) { // Waiting for the server to receive the connection
            peer.checkActiveConnections();
            if (peer.get("192.168.1.9") != null) {
                peer.get("192.168.1.9").receiveFile(fileOut);
                break;
            }
        }
    }
}