package connectivity.connection;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

/**
 * This class is used to receive a file which was sent through a socket
 */
public class ConnectionReceiver {
    /**
     * The decorator used for receiving a file from a socket
     */
    private final DataInputStream reader;

    /**
     * The constructor initializes the reader, receiving a socket and set the reader to read from that socket's input stream
     * @param socket The socket through which a file was sent and received
     * @throws IOException
     */
    public ConnectionReceiver(Socket socket) throws IOException {
        this.reader = new DataInputStream(socket.getInputStream());
    }

    /**
     * This is the only method of class which use is to receive a file from socket and returning the new path of that file
     * @param to The path of new received file
     * @return The path of a file that was read from socket
     * @throws IOException
     */
    public synchronized Path receiveFile(Path to) throws IOException {

        //available bytes... if it is closed... this gives 0 anyway
        if(reader.available() == 0)
            return null;
        Path relativePath = Paths.get(reader.readUTF());
        Path path = to.resolve(relativePath);
        System.out.println("receiving: " + path);
        long modified = reader.readLong();
        long size = reader.readLong();

        Files.createDirectories(path.getParent());

        try(OutputStream os = new FileOutputStream(path.toFile())){
            final int chunk = 1024;
            byte[] buffer = new byte[chunk];

            while (size > 0){
                int readSize = (int) Math.min(chunk, size);
                int actualRead = reader.read(buffer, 0, readSize);

                if(actualRead == 0)
                    break;

                os.write(buffer, 0, actualRead);
                size -= actualRead;
            }

            Files.setLastModifiedTime(path, FileTime.fromMillis(modified));

        } catch (Exception e){
            System.out.println("Receive Exception: " + e.getMessage());
            reader.skipNBytes(size);
        }

        System.out.println("done receive");
        return relativePath;
    }
}