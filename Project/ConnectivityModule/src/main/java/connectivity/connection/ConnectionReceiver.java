package connectivity.connection;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class ConnectionReceiver {
    private final DataInputStream reader;

    public ConnectionReceiver(Socket socket) throws IOException {
        this.reader = new DataInputStream(socket.getInputStream());
    }

    public synchronized Path receiveFile(Path to) throws IOException {

        //available bytes... if it is closed... this gives 0 anyway
        if(reader.available() == 0)
            return null;

        System.out.println("About to get a file");

        String fileName = reader.readUTF();
        Path path = to.resolve(fileName);
        System.out.println("path: " + path);
        long modified = reader.readLong();
        long size = reader.readLong();

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
            reader.skipNBytes(size);
        }

        System.out.println("done receive");

        return path;
    }
}