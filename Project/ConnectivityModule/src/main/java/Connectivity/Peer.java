package Connectivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Peer{
    protected int port;
    protected ServerSocket serverSocket;
    List<Connection> connections = new ArrayList<>();

    public Peer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public List<String> getDevices() throws InterruptedException, IOException {
        return getIPsUsingPing();
        //return getIPsUsingCMD();
    }

    private List<String> getIPsUsingPing() throws InterruptedException {
        byte[] ip = new byte[3];
        List<String> IpList = new ArrayList<String>();
        try {
            ip = InetAddress.getLocalHost().getAddress();
        } catch (Exception ignored) {}

        for(int i=0;i<=255;i++)
        {
            final int j = i;  // i as non-final variable cannot be referenced from inner class
            byte[] finalIp = ip;
            // new thread for parallel execution
            new Thread(() -> {
                try {
                    finalIp[3] = (byte)j;
                    InetAddress address = InetAddress.getByAddress(finalIp);
                    String output = address.toString().substring(1);
                    if (address.isReachable(5000)) {
                        IpList.add(output);
                    }
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(1000);
        return IpList;
    }

    private List<String> getIPsUsingCMD() throws IOException {
        List<String> IPsList = new ArrayList<>();
        //String command = "powershell.exe  your command";
        //Getting the version
        String command = "powershell.exe  arp -a";
        // Executing the command
        Process powerShellProcess = Runtime.getRuntime().exec(command);
        // Getting the results
        powerShellProcess.getOutputStream().close();
        String line;
        // System.out.println("Standard Output:");
        BufferedReader stdout = new BufferedReader(new InputStreamReader(
                powerShellProcess.getInputStream()));
        while ((line = stdout.readLine()) != null) {
            if (line.contains("Interface")){
                //System.out.println(line.substring(11, 26));
                IPsList.add(line.substring(11, 26));
            }

            if (line.contains("dynamic")) {
                //System.out.println(line.substring(2, 17));
                IPsList.add(line.substring(2, 17));
            }
        }
        stdout.close();
        //System.out.println("Standard Error:");
        BufferedReader stderr = new BufferedReader(new InputStreamReader(
                powerShellProcess.getErrorStream()));
        while ((line = stderr.readLine()) != null) {
            System.out.println(line);
        }
        stderr.close();
        //System.out.println("Done");
        return IPsList;
    }

    public Connection incomingFile(){
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (var connection : connections) {

                    String nameIncomingFile  = String.valueOf(connection.getNameIncomingFile());
                    //!! Add exception here
                    if (nameIncomingFile != null)
                        return connection;
                }
                wait(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }

    public void waitingConnection(){

        Socket clientSocket;
        try {
            clientSocket = this.serverSocket.accept();

        } catch (IOException e) {
            throw new RuntimeException( "Error accepting client connection", e );
        }

        //!! more exceptions
        try {
            System.out.println("New client!");
            connections.add(new LocalConnection(clientSocket));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server Stopped.") ;
    }

    public void connectDevice(int deviceNumber,int devicePort) throws IOException {
        //!! change with dynamic ipv4
        Connection connection = new LocalConnection(new Socket("192.168.100." + deviceNumber, devicePort));

        connections.add(connection);
    }

    public Connection get(int index){
        return connections.get(index);
    }
}
