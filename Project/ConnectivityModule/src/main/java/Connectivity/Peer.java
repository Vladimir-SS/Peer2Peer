package Connectivity;

import Exceptions.PeerAlreadyConnected;
import Exceptions.PeerDisconnectedException;
import Exceptions.PortException;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class Peer {
    protected int port;
    private ConnectionsManager connectionsManager = null;
    private final Broadcast broadcast;

    public Peer() throws IOException {
        int port = this.getPort();
        this.port = port;
        this.broadcast = new Broadcast(port, 5);
        startConnectionsManager();
    }

    private void startConnectionsManager() {
        connectionsManager = ConnectionsManager.getInstance(port);
        new Thread(connectionsManager).start();
    }

    public Set<InetAddress> getDevices() throws InterruptedException, IOException {
        System.out.println("Searching for connections!");
        var addresses = broadcast.getAddresses(10, false);
        broadcast.close();
        return addresses;
    }

    public synchronized void checkActiveConnections() {
        for (Map.Entry<String, Connection> connection : connectionsManager.connections.entrySet()) {
            System.out.println(connection.getValue().getName());
        }
    }

    public synchronized Connection incomingFile() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Map.Entry<String, Connection> connection : connectionsManager.connections.entrySet()) {

                    String nameIncomingFile = String.valueOf(connection.getValue().getNameIncomingFile());
                    //!! Add exception here
                    if (nameIncomingFile != null)
                        return connection.getValue();
                }
                wait(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }

    private synchronized boolean isConnected(String deviceIP) {
        for (Map.Entry<String, Connection> connection : connectionsManager.connections.entrySet()) {
            if (connection.getValue().getName().equals(deviceIP)) {
                return true;
            }
        }
        return false;
    }

    public void connectDevice(String deviceIP, int devicePort) throws IOException, PeerAlreadyConnected {
        //!! change with dynamic ipv4
        if (!isConnected(deviceIP)) {
            Connection connection = new LocalConnection(new Socket(deviceIP, devicePort));
            connection.setName(deviceIP);
            connectionsManager.connections.put(connection.getName(), connection);
        } else {
            throw new PeerAlreadyConnected();
        }
    }

    public Connection get(String ip) throws PeerDisconnectedException {
        if (isConnected(ip)) {
            return connectionsManager.connections.get(ip);
        } else {
            throw new PeerDisconnectedException(ip);
        }
    }

    public int getPort() throws PortException {
        Scanner input = new Scanner(System.in);
        System.out.println("*** To start a connection enter a port ***:");
        int port = Integer.parseInt(input.nextLine());

        while (!isAvailable(port)) {
            System.out.println("The port " + port + " is already used. Enter a new port: ");
            port = Integer.parseInt(input.nextLine());
        }
        return port;
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
             DatagramSocket tempDatagram = new DatagramSocket(port);) {
            tempSocket.setReuseAddress(true);
            tempDatagram.setReuseAddress(true);
            return true;
        } catch (IOException ignored) {
        }
        return false;
    }
}