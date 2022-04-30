package Connectivity;

import java.io.FileOutputStream;
import java.io.IOException;

public interface Connection {
    FileOutputStream getNameIncomingFile();
    void receiveFile(FileOutputStream foStream);
    void sendFile(String fileName) throws IOException;
}