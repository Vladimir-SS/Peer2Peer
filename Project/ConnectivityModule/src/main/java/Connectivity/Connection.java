package Connectivity;

import java.io.*;
import java.net.Socket;
import java.util.List;

public interface Connection extends Closeable {
    FileOutputStream getNameIncomingFile();
    void receiveFile(FileOutputStream foStream);
    void sendFile(String fileName) throws IOException;
    String getName();
    String getAddress();
}