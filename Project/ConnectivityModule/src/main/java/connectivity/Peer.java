package connectivity;

import connectivity.broadcast.Broadcast;
import connectivity.connection.Connection;
import connectivity.connection.ConnectionsManager;
import connectivity.connection.LocalConnection;
import connectivity.exceptions.PeerAlreadyConnected;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.nio.file.Path;
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
        return broadcast.getAddresses(5, async);
    }

    public List<Connection> getConnectedDevices(){
        return new ArrayList<>(connectionsManager.getConnections());
    }

    public synchronized Map.Entry<Connection, Path> incomingFile(Path to) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Connection connection : connectionsManager.getConnections()) {

                    try {
                        Path path  = connection.receiveFile(to);
                        if(path != null)
                            return new AbstractMap.SimpleImmutableEntry<>(connection, path);
                    } catch (IOException e) {
                        return new AbstractMap.SimpleImmutableEntry<>(connection, null);
                    }
                }
                wait(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }

    private synchronized boolean isConnected(Connection connection) {
        return connectionsManager.getConnections().contains(connection);
    }

    public void connectDevice(InetAddress device) throws IOException, PeerAlreadyConnected {
        LocalConnection connection = new LocalConnection(device, getPort());

        if (!isConnected(connection)) {
            connectionsManager.getConnections().add(connection);
        } else {
            throw new PeerAlreadyConnected();
        }
    }

    public void disconnectDevice(Connection device) {
        connectionsManager.getConnections().remove(device);
        try {
            device.close();
        } catch (IOException ignored) {
        }
    }

    public int getPort(){
        return connectionsManager.getServerPort();
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