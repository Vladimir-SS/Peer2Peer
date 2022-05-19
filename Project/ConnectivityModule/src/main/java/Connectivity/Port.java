package Connectivity;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Scanner;

public class Port {

    public static int getPort() {
        Scanner input = new Scanner(System.in);
        System.out.println("*** To start a connection enter a port ***:");
        int port = Integer.parseInt(input.nextLine());

        while(!Port.isAvailable(port)) {
            System.out.println("The port " + port + " is already used. Enter a new port: ");
            port = Integer.parseInt(input.nextLine());
        }
        return port;
    }

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
