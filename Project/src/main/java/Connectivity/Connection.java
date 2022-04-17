package Connectivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface Connection {
    FileOutputStream getNameIncomingFile();
    void receiveFile(FileOutputStream foStream);
    void sendFile(String fileName) throws IOException;
}