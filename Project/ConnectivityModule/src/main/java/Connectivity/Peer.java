package Connectivity;

import Exceptions.PeerAlreadyConnected;
import Exceptions.PeerDisconnectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Peer{
    protected int port;
    private ConnectionsManager connectionsManager = null;
    private Broadcast broadcast;

    public Peer(int port) throws IOException {
        this.port = port;
        this.broadcast = new Broadcast(port,5);
        startConnectionsManager();
    }

    private void startConnectionsManager() {
        connectionsManager = ConnectionsManager.getInstance(port);
        new Thread(connectionsManager).start();
    }

    public InetAddress getIP() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    public Set<InetAddress> getDevices() throws InterruptedException, IOException {
        System.out.println("Searching for connections!");
        var addresses = broadcast.getAddresses(10, false);
        broadcast.close();
        return addresses;
        //return getIPsUsingPing();
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

    static Map<String,String> devices = new HashMap<>();

    public static void getDevicesFromSubnet(String subnet) throws IOException {
        int timeout=1000;
        for(int i = 1; i < 255; i++)
        {
            int finalI = i;
            new Thread(() -> {
                String host=subnet + "." + finalI;
                try {
                    if (InetAddress.getByName(host).isReachable(timeout)){
                        System.out.println(InetAddress.getByName(host).getHostName() + ", IP adress : " + host + " is reachable");
                        devices.put(host,InetAddress.getByName(host).getHostName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public synchronized void checkActiveConnections() {
        for (Map.Entry<String,Connection> connection : connectionsManager.connections.entrySet()) {
            System.out.println(connection.getValue().getName());
        }
    }

    public synchronized Connection incomingFile(){
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (Map.Entry<String,Connection> connection : connectionsManager.connections.entrySet()) {

                    String nameIncomingFile  = String.valueOf(connection.getValue().getNameIncomingFile());
                    //!! Add exception here
                    if (nameIncomingFile != null)
                        return connection.getValue();
                }
                wait(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }

    private synchronized boolean isConnected(String deviceIP){
        for (Map.Entry<String,Connection> connection : connectionsManager.connections.entrySet()) {
            if (connection.getValue().getName().equals(deviceIP)) {
                return true;
            }
        }
        return false;
    }

    public void connectDevice(String deviceIP,int devicePort) throws IOException, PeerAlreadyConnected {
        //!! change with dynamic ipv4
        if (!isConnected(deviceIP)) {
            Connection connection = new LocalConnection(new Socket(deviceIP, devicePort));
            connection.setName(deviceIP);
            connectionsManager.connections.put(connection.getName(), connection);
        } else {
            throw new PeerAlreadyConnected();
        }
    }

    public Connection get(String ip) throws PeerDisconnectedException {
        if (isConnected(ip)) {
            return connectionsManager.connections.get(ip);
        } else {
            throw new PeerDisconnectedException(ip);
        }
    }
}