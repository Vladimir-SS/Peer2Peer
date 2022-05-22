package Connectivity;

import Exceptions.PeerAlreadyConnected;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class Peer implements Closeable {
    private final ConnectionsManager connectionsManager;
    private final Broadcast broadcast;

    public Peer(int port) throws IOException {
        this.broadcast = new Broadcast(port, 5);
        connectionsManager = ConnectionsManager.getInstance(port);
        new Thread(connectionsManager).start();
    }


    /**
     *
     * @param async If this variable is set to "true",
     *              the function returns an empty container which will be later filled.
     * @return Available devices after listening to broadcasts.
     * @throws IOException Broadcasting is not working
     */
    public Set<InetAddress> findDevices(boolean async) throws IOException {
        var addresses = broadcast.getAddresses(10, async);
        broadcast.close();
        return addresses;
    }

    public List<Connection> getConnectedDevices(){
        return new ArrayList<>(connectionsManager.connections);
    }

    public synchronized Connection incomingFile() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Connection connection : connectionsManager.connections) {

                    String nameIncomingFile = String.valueOf(connection.getNameIncomingFile());
                    //TODO: Add exception here
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

    private synchronized boolean isConnected(Connection connection) {
        return connectionsManager.connections.contains(connection);
    }

    public void connectDevice(InetAddress device) throws IOException, PeerAlreadyConnected {
        LocalConnection connection = new LocalConnection(device, getPort());

        if (!isConnected(connection)) {
            connectionsManager.connections.add(connection);
        } else {
            throw new PeerAlreadyConnected();
        }
    }

    public int getPort(){
        return connectionsManager.serverPort;
    }

    public static boolean isAvailable(int port) {
        /*
        TODO
        Add IllegalArgumentException without ruining the code
        if(port<1024||port>65353){
            throw new IllegalArgumentException("Not a valid port number : "+port);
        }
         */
        try (ServerSocket tempSocket = new ServerSocket(port);
             DatagramSocket tempDatagram = new DatagramSocket(port)) {
            tempSocket.setReuseAddress(true);
            tempDatagram.setReuseAddress(true);
            return true;
        } catch (IOException ignored) {
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        broadcast.close();
        //TODO: Close connectionsManager
        connectionsManager.stop();
    }
}