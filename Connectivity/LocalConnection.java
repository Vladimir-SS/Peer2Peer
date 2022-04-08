package Connectivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class LocalConnection implements Connection {
    protected Socket clientSocket;

    InputStream reader;
    OutputStream writer;

    public LocalConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

        this.reader  = clientSocket.getInputStream();
        this.writer = clientSocket.getOutputStream();
    }

    @Override
    public String getNameIncomingFile() {
        return "haha.txt";
    }

    @Override
    public void receiveFile(String fileName) {
        // read file

        // read file in new thread

        // vvvvvvvvv TEST -- Delete when u start working

        try {
            System.out.println(Arrays.toString(reader.readAllBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFile(String fileName) {
        // send file size

        // send file in new Thread

        //vvvvvvvv Test - Delete when u start working
        System.out.println("I'll send data");
        try {
            writer.write("Custom Message!".getBytes());
        } catch (IOException e) {
            System.err.println("Nope, smth not working :(");
        }
    }
}
