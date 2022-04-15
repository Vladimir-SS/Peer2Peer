package Connectivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Peer{
    protected int port;
    protected ServerSocket serverSocket;
    List<Connection> connections = new ArrayList<>();

    public Peer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public Connection incomingFile(){
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (var connection : connections) {

                    String nameIncomingFile  = String.valueOf(connection.getNameIncomingFile());
                    //!! Add exception here
                    if (nameIncomingFile != null)
                        return connection;
                }
                wait(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }

    public void waitingConnection(){

            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();

            } catch (IOException e) {
                throw new RuntimeException( "Error accepting client connection", e );
            }

            //!! more exceptions
            try {
                connections.add(new LocalConnection(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Server Stopped.") ;
        }

    public void connectDevice(int deviceNumber) throws IOException {
        //!! change with dynamic ipv4
        Connection connection = new LocalConnection(new Socket("192.168.1." + deviceNumber, port));

        connections.add(connection);
    }

    public Connection get(int index){
        return connections.get(index);
    }
}
