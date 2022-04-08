package Connectivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Connection {
    String getNameIncomingFile();
    void receiveFile(String fileName);
    void sendFile(String fileName);
}
