package console;

import console.commands.Command;
import console.commands.DeviceCommand;
import connectivity.Peer;
import console.commands.ExitCommand;
import console.commands.SyncCommand;
import george.ConnectivityResident;
import george.SynchronizedDirectory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//TODO: MOVE THIS IN HIS OWN PACKAGE

/**
 * This class is used to establish a communication between the application
 * and the user through the console.
 * It displays instructions for the user and reads the commands given invoking
 * the methods responsible for interpreting and executing them.
 */

public class ConsoleInterface {

    static final private Scanner input = new Scanner(System.in);
    static private Peer peer;
    static private SynchronizedDirectory synchronizedDirectory;
    private static final Map<String, Command> commands = new HashMap<>(){{
        put("device", new DeviceCommand());
        put("exit", new ExitCommand());
        put("sync", new SyncCommand());
    }};


    /**
     * This method is used to instantiate a new Peer with a given port.
     * It checks if the port is available and if a connection can be created with it.
     */
    private static void setPort(){
        do {
            System.out.println("*** To start a connection enter a port ***:");


            try {
                int port = Integer.parseInt(input.nextLine());
                System.out.println("Port: " + port);
                if(!Peer.isAvailable(port)) {
                    System.out.println("Port already in use");
                    continue;
                }
                peer = new Peer(port);
                break;
            } catch (Exception e) {
                System.out.println("Peer Error: " + e.getMessage());
            }
        }while (true);
    }


    /**
     * This method is used to set the path to the folder destined for
     * syncronization with the other devices, making sure that the
     * path is valid.
     */
    private static void setPath(){
        do{
            System.out.println("*** Enter path to synchronized folder ***:");
            Path path = Paths.get(input.nextLine());
            System.out.println("Path: " + path);

            try {
                if(Files.notExists(path)) {
                    System.out.println("Path doesn't exist");
                    continue;
                }
                synchronizedDirectory = new SynchronizedDirectory(path);
                break;
            } catch (Exception e) {
                System.out.println("Path Error: " + e.getMessage());
            }
        }while(true);
    }


    /**
     * The main function which will invoke the methods responsible for setting
     * the port and the path.
     * This is also the function used to read the commands given from the user.
     * @param args The standard main function signature.
     */
    public static void main(String[] args) {

        setPort();
        setPath();

        ConnectivityResident cr = new ConnectivityResident();
        cr.setSynchronizedDirectory(synchronizedDirectory);
        cr.setPeer(peer);
        cr.start();

        commands.forEach((k, v) -> v.setApp(cr));

        while (true) {
            String line = input.nextLine();
            String[] params = line.split(" ");

            if(params.length == 0)
                continue;

            if(!commands.containsKey(params[0]))
            {
                System.out.println("Commands not found");
                continue;
            }

            Command command = commands.get(params[0]);
            try {
                command.run(Arrays.copyOfRange(params, 1, params.length));
            } catch (Exception e) {
                System.out.println(params[0] + ": " + e.getMessage());
            }
        }
    }
}
