package connectivity.connection;

import connectivity.exceptions.PortException;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * This class keeps a track of all connections that are accepted by a node, managing them.
 */
public class ConnectionsManager implements Runnable {

    /**
     * This is private variable used as a inner instace of class and ensures the existence of a single instace
     * of the class at any given moment. The default value of this instance is null.
     */
    private static volatile ConnectionsManager instance = null;
    /**
     * This variable represents the port on which the connection is made. The default value of port is 8080.
     */
    protected int serverPort;
    /**
     * This variable represents the socket used by the connection. The default value of socket is null.
     */
    protected ServerSocket serverSocket = null;
    /**
     * This variable is used to store the thread that is used by the class in order to accept new connections.
     */
    protected Thread runningThread = null;
    /**
     * This variable keeps a track of all connections that are realised for a certain client address.
     */
    protected Set<Connection> connections = new HashSet<>();

    /**
     * The method returns the connections variable which keeps a track of all connections that are realised
     * @return The current list of connections that are realised
     */
    public Set<Connection> getConnections() {
        return connections;
    }

    /**
     * This constructor will simply set the variable serverPort to the value of parameter port.
     * @param port The value which is assigned to serverPort variable
     */
    private ConnectionsManager(int port) {
        this.serverPort = port;
    }

    /**
     * This method will return an istance of class. If the variable instance of class is null both locally and as well
     * as in all the threads that use the class then the instance variable is initialized as a new instance of class,
     * calling the constructor with the parameter port.
     * @param port The port used to create a new instance of ConnectionsManager class
     * @return An instance of ConnectionsManager class
     */
    public static ConnectionsManager getInstance(int port) {
        if (instance == null) {
            synchronized (ConnectionsManager.class) {
                if (instance == null) {
                    instance = new ConnectionsManager(port);
                }
            }
        }

        return instance;
    }

    /**
     * This method override the run method of the Runnable interface. Every new thread calling a instace of this class
     * will use the code write in this method. At the beginning of method the variable runningThread is initialized
     * with the value of the thread that is currently running the method and a new socket is open, calling the
     * openServerSocket method. After that, in a loop, new connections are expected and when a new connection is made
     * a new LocalConnection will be added on the connections map variable, having as key the IP address of the node
     * requesting the connection.
     */
    @Override
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

            try {
                LocalConnection connection = new LocalConnection(clientSocket);
                if(!connections.contains(connection)) {
                    connections.add(connection);
                    System.out.println("Peer: " + clientSocket.getRemoteSocketAddress().toString() + " connected!");
                }
                else {
                    //TODO: connections as HASHMAP<INTEGER/STRING, CONNECTION>
                    connections .stream()
                            .filter(c -> c.equals(connection))
                            .findFirst()
                            .ifPresent(c -> {
                                try {
                                    c.close();
                                } catch (IOException ignored) {
                                }
                            });
                    connections.add(connection);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Connection manager stopped.");
    }

    /**
     * This method will close the socket for the node who was waiting for connections.
     */
    public synchronized void stop() {
        try {
            this.serverSocket.close();
        } catch (IOException ignored) {

        }
    }

    /**
     * This method will open a new socket, initializing the variable serverSocket with a new instance of ServerSocket
     * type, using as port the serverPort variable.
     * @throws PortException A custom exception thrown if the given port is already in use
     */
    private void openServerSocket() throws PortException {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        }  catch (IOException e) {
            throw new PortException(serverPort);
        }
    }

    /**
     * This method will return the serverPort variable
     * @return The serverPort variable of class
     */
    public int getServerPort() {
        return serverPort;
    }
}