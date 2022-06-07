package connectivity.connection;

import java.io.*;
import java.nio.file.Path;

public interface Connection extends Closeable {
    Path receiveFile(Path to) throws IOException;
    void sendFile(Path root, Path relativePath) throws IOException;
    String getName();
    String getAddress();
}