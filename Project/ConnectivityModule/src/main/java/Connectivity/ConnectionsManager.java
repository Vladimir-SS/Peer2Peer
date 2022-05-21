package Connectivity;

import Exceptions.PortException;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ConnectionsManager implements Runnable {

    private static volatile ConnectionsManager instance = null;
    protected int serverPort;
    protected ServerSocket serverSocket = null;
    protected Thread runningThread = null;
    protected Map<String,Connection> connections = new HashMap<>();

    private ConnectionsManager(int port) {
        this.serverPort = port;
    }

    protected static ConnectionsManager getInstance(int port) {
        if (instance == null) {
            synchronized (ConnectionsManager.class) {
                if (instance == null) {
                    instance = new ConnectionsManager(port);
                }
            }
        }

        return instance;
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        try {
            openServerSocket();
        } catch (PortException e) {
            e.printStackTrace();
        }
        Socket clientSocket;
        while (!serverSocket.isClosed()) {
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if (serverSocket.isClosed()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            System.out.println("Peer: " + clientSocket.getRemoteSocketAddress().toString() + " connected!");
            try {
                String connName = clientSocket.getRemoteSocketAddress().toString();
                connections.put(connName.substring(connName.indexOf("/") + 1, connName.indexOf(":"))
                        ,new LocalConnection(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Connection manager stopped.");
    }

    public synchronized void stop() {
        try {
            this.serverSocket.close();
        } catch (IOException ignored) {

        }
    }

    private void openServerSocket() throws PortException {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        }  catch (IOException e) {
            throw new PortException(serverPort);
        }
    }

}