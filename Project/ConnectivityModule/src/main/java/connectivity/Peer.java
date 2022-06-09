package connectivity;

import connectivity.broadcast.Broadcast;
import connectivity.connection.Connection;
import connectivity.connection.ConnectionsManager;
import connectivity.connection.LocalConnection;
import connectivity.exceptions.BroadcastFailedException;
import connectivity.exceptions.DeviceAlreadyConnectedException;
import connectivity.exceptions.DeviceConnectException;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.nio.file.Path;
import java.util.*;

public class Peer implements Closeable {
    private final ConnectionsManager connectionsManager;
    private final Broadcast broadcast;

    public Peer(int port) throws PortUnreachableException, SocketException {
        if(!isAvailable(port))
            throw new PortUnreachableException("port " + port + " is not available");

        this.broadcast = new Broadcast(port, 5);
        connectionsManager = ConnectionsManager.getInstance(port);
        new Thread(connectionsManager).start();
    }


    public Set<InetAddress> findDevices() throws BroadcastFailedException {
        return broadcast.getAddresses(5);
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

    public void connectDevice(InetAddress device) throws DeviceAlreadyConnectedException, DeviceConnectException {
        LocalConnection connection;
        try {
            connection = new LocalConnection(device, getPort());
        } catch (IOException e) {
            throw new DeviceConnectException(e);
        }

        var connections = connectionsManager.getConnections();
        if (!connections.contains(connection))
            connections.add(connection);
        else
            throw new DeviceAlreadyConnectedException();
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