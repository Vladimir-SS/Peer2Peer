package Connectivity;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LocalConnection implements Connection {
    protected Socket clientSocket;
    private String name;

    InputStream reader;
    OutputStream writer;

    /*defalt*/ LocalConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.reader  = clientSocket.getInputStream();
        this.writer = clientSocket.getOutputStream();
    }

    /*defalt*/ LocalConnection(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));

    }

    @Override
    //formatul numelui: &nume.ext#dim_fisier_bytes*
    //ar trebui sa existe un hand-shaking aici (??)
    public FileOutputStream getNameIncomingFile() {
        try {
            String fileName = Arrays.toString(reader.readAllBytes());
            String file = fileName.substring(fileName.indexOf("&") + 1, fileName.indexOf("#"));
            String lengthFile = fileName.substring(fileName.indexOf("#") + 1, fileName.indexOf("*"));
            int fileSize = Integer.parseInt(lengthFile);

            return createFile(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void receiveFile(FileOutputStream foStream) {
        byte[] readBuffer = new byte[8192];
        System.out.println("Receiving file");
        Thread readRunnable = new Thread(() -> {
            while (true) {
                try {
                    int num = reader.read(readBuffer);
                    if (num > 0) {
                        byte[] tempArray = new byte[num];
                        System.arraycopy(readBuffer, 0, tempArray, 0, num);
                        writeToFile(foStream, tempArray);
                    } else {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        readRunnable.start();
    }

    private FileOutputStream createFile(String fileName) {
        File dstFile = new File("./" + fileName);
        try {
            return new FileOutputStream(dstFile);
        } catch (FileNotFoundException fn) {
            fn.printStackTrace();
        }
        return null;
    }

    private void writeToFile(FileOutputStream foStream, byte[] buff) {
        try {
            foStream.write(buff);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void sendFile(String fileName) throws IOException {
        // send file size
        byte[] buffer = new byte[8192];
        String filePath = "";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Thread sendFile = new Thread(() -> {
            byte[] buffer1 = new byte[8192];
            while (true) {
                try {

                    int bytes = fileInputStream.read(buffer1,0, buffer1.length);
                    if(bytes>0){
                        writer=clientSocket.getOutputStream();
                        writer.write(buffer1,0,bytes);
                    }else{
                        break;
                    }

                } catch (IOException e) {
                    System.out.println("Error sending file");
                    e.printStackTrace();
                }
            }
            System.out.println("File successfully send");
        });
        sendFile.start();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}