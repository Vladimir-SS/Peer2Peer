package connectivity.connection;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;


public class ConnectionSender {
    private final DataOutputStream writer;

    public ConnectionSender(Socket socket) throws IOException {
        this.writer = new DataOutputStream(socket.getOutputStream());
    }

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
