package Connectivity;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Port {

    public static boolean isAvailable(int port) {
        ServerSocket tempSocket = null;
        DatagramSocket tempDatagram = null;

        try {
            tempSocket = new ServerSocket(port);
            tempSocket.setReuseAddress(true);
            tempDatagram = new DatagramSocket(port);
            tempDatagram.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (tempDatagram != null) {
                tempDatagram.close();
            }
            if (tempSocket != null) {
                try {
                    tempSocket.close();
                } catch (IOException e) {

                }
            }
        }
        return false;
    }
}
