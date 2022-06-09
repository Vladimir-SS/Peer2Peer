package connectivity.connection;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class is used to send a file through a socket
 */
public class ConnectionSender {
    /**
     * The decorator used for writing in a file
     */
    private final DataOutputStream writer;

    /**
     * The constructor initializes the writer, receiving a socket and set the writer to write in that socket's output stream
     * @param socket The socket through which a file was sent and received
     * @throws IOException
     */
    public ConnectionSender(Socket socket) throws IOException {
        this.writer = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * This is the only method of class which use is to send a file as a stream of bytes through a socket
     * @param root The path to the root of a file
     * @param relative The relative path to a file (the full path)
     * @throws IOException
     */
    public synchronized void sendFile(Path root, Path relative) throws IOException {
        String pathName = relative.toString();
        Path file = root.resolve(relative);

        System.out.println("sending: " + pathName);

        try(InputStream is = new FileInputStream(file.toFile())){
            writer.writeUTF(pathName);
            writer.writeLong(Files.getLastModifiedTime(file).toMillis());
            writer.flush();

            final int chunk = 1024;
            byte[] buffer = new byte[chunk];
            long size = Files.size(file);
            writer.writeLong(size);

            while(size > 0){
                int sendSize = is.read(buffer);
                if(sendSize == 0)
                    break;
                System.out.println();
                writer.write(buffer, 0, sendSize);
                size -= sendSize;
            }

            writer.flush();
        }
        System.out.println("done send");
    }

}
