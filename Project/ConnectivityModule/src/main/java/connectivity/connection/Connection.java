package connectivity.connection;

import java.io.*;
import java.nio.file.Path;

/**
 * This interface ensures file transfer between devices connected to the LAN.
 */
public interface Connection extends Closeable {
    /**
     * This method is used to received a path of a file and return the new path of file after completely receiving the
     * content of file
     * @param to Path of a file
     * @return A path to the new received file
     * @throws IOException
     */
    Path receiveFile(Path to) throws IOException;
    void sendFile(Path root, Path relativePath) throws IOException;

    /**
     * This method is used to return the current host file of connection
     * @return The host name
     */
    String getName();

    /**
     * This method is used to return the address of host
     * @return Address of the host
     */
    String getAddress();
}