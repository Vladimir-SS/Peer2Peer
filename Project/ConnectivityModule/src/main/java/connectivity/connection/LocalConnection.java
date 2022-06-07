package connectivity.connection;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class LocalConnection implements Connection {
    protected Socket clientSocket;
    protected ConnectionReceiver receiver;
    protected ConnectionSender sender;

    /*default*/ LocalConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.receiver = new ConnectionReceiver(clientSocket);
        this.sender = new ConnectionSender(clientSocket);
    }

    public LocalConnection(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }


    @Override
    public Path receiveFile(Path to) throws IOException {
        return receiver.receiveFile(to);
    }

    @Override
    public void sendFile(Path root, Path relativePath) throws IOException {
        sender.sendFile(root, relativePath);
    }

    @Override
    public String getName() {
        return clientSocket.getInetAddress().getHostName();
    }

    @Override
    public String getAddress() {
        return clientSocket.getInetAddress().getHostAddress();
    }

    @Override
    public void close() throws IOException {
        clientSocket.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalConnection that = (LocalConnection) o;
        if(clientSocket.equals(that.clientSocket))
            return true;

        return clientSocket.getLocalAddress().equals(that.clientSocket.getLocalAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientSocket.getLocalAddress());
    }

    @Override
    public String toString() {
        return getName() + " | " + getAddress();
    }
}