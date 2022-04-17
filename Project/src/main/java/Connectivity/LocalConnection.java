package Connectivity;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LocalConnection implements Connection {
    protected Socket clientSocket;

    InputStream reader;
    OutputStream writer;

    public LocalConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

        this.reader  = clientSocket.getInputStream();
        this.writer = clientSocket.getOutputStream();
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
        Thread readRunnable = new Thread() {
            public void run() {
                while (true) {
                    try {
                        int num = reader.read(readBuffer);
                        if (num > 0) {
                            byte[] tempArray = new byte[num];
                            System.arraycopy(readBuffer, 0, tempArray, 0, num);
                            writeToFile(foStream, tempArray);
                        } else if (num < 0){
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
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
        Thread sendFile = new Thread() {
            public void run() {
                byte[] buffer = new byte[8192];
                try {
                    FileInputStream fileInputStream = new FileInputStream(fileName);
                    int bytes = fileInputStream.read(buffer, 0, buffer.length);
                    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                    fileOutputStream.write(bytes);
                    System.out.println("File successfully send");
                } catch (IOException e) {
                    System.out.println("Error sending file");
                    e.printStackTrace();
                }
            }
        };
        sendFile.start();

        // send file in new Thread

        //vvvvvvvv Test - Delete when u start working
//        System.out.println("I'll send data");
//        try {
//            writer.write("Custom Message!".getBytes());
//        } catch (IOException e) {
//            System.err.println("Nope, smth not working :(");
//        }
    }
}
