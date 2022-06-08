package connectivity.connection;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class simulates a local connection created by an end-point of our application, and manages all tasks that a normal
 * user may encounter
 */
public class LocalConnection implements Connection {
    /**
     * This is the socket through our client will connect to the application
     */
    protected Socket clientSocket;
    /**
     * This variable is used to describe an object of type ConnectionReceiver, class which is used to receive data from
     * another socket
     */
    protected ConnectionReceiver receiver;
    /**
     * This variable is used to describe an object of type ConnectionSender, class which is used to send data to another
     * socket
     */
    protected ConnectionSender sender;

    /**
     * The constructor initializes the socket and the receiver and sender of class. This is the default constructor of class
     * @param clientSocket The socket through which the connection will be made
     * @throws IOException
     */
    /*default*/ LocalConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.receiver = new ConnectionReceiver(clientSocket);
        this.sender = new ConnectionSender(clientSocket);
    }

    /**
     * The constructor in used only to set the socket through which the connection will be made, receiving as parameter
     * an IP address and a port
     * @param address The IP address used to create a new socket
     * @param port The port used to create a new socket
     * @throws IOException
     */
    public LocalConnection(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    /**
     * The method use the receiver to receive a new file and calls the receiveFile() method of the receiver
     * @param to Path of a file
     * @return A path to the new received file
     * @throws IOException
     */
    @Override
    public Path receiveFile(Path to) throws IOException {
        return receiver.receiveFile(to);
    }

    /**
     * The method use the sender to send a file through socket and calls the sendFile() method of the sender
     * @param root The path to the root of a file
     * @param relativePath The relative path to a file (the full path)
     * @throws IOException
     */
    @Override
    public void sendFile(Path root, Path relativePath) throws IOException {
        sender.sendFile(root, relativePath);
    }

    /**
     * This is a getter just for returning the host name of the clientSocket
     * @return A string representing the name of the host of clientSocket
     */
    @Override
    public String getName() {
        return clientSocket.getInetAddress().getHostName();
    }

    /**
     * This is a getter just for returning the address of the clientSocket
     * @return A string representing the address of clientSocket's host
     */
    @Override
    public String getAddress() {
        return clientSocket.getInetAddress().getHostAddress();
    }

    /**
     * The method is used to access and close the clientSocket
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        clientSocket.close();
    }

    /**
     * The method overrides the equals() method of class Object in order to compare two object of type LocalConnection
     * @param o An object
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalConnection that = (LocalConnection) o;
        if(clientSocket.equals(that.clientSocket))
            return true;

        return clientSocket.getLocalAddress().equals(that.clientSocket.getLocalAddress());
    }

    /**
     * The method overrides the hashCode() method of class Object in order to get a correct value of a hash code of an
     * object of type LocalConnection. The hash is based of clientSocket local address.
     * @return A hash code used
     */
    @Override
    public int hashCode() {
        return Objects.hash(clientSocket.getLocalAddress());
    }

    /**
     * The method overrides the toString() method of class Object in order to get a representation of an instance of type
     * LocalConnection
     * @return A string representing a text representation of an instance of type LocalConnection
     */
    @Override
    public String toString() {
        return getName() + " | " + getAddress();
    }
}