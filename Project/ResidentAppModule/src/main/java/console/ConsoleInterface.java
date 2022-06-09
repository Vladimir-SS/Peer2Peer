package console;

import console.commands.Command;
import console.commands.DeviceCommand;
import connectivity.Peer;
import console.commands.SyncCommand;
import george.resident.exceptions.BadSyncDirectory;
import george.resident.sync.ConnectivityResident;
import george.resident.SynchronizedDirectory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//TODO: MOVE THIS IN HIS OWN PACKAGE

public class ConsoleInterface {
    private static final String manual = "peer <port> <path> [--watch]";

    static final private Scanner input = new Scanner(System.in);

    private static final Map<String, Command> commands = new HashMap<>(){{
        put("device", new DeviceCommand());
        put("sync", new SyncCommand());
    }};


    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Not enough arguments: ");
            System.out.println(manual);
        }

        int port = Integer.parseInt(args[0]);
        System.out.println("Port: " + port);

        Path path = Paths.get(args[1]);
        System.out.println("Path: " + port);


        try {
            final ConnectivityResident cr = new ConnectivityResident(port, path);
            commands.forEach((k, v) -> v.setApp(cr));
        } catch (BadSyncDirectory e) {
            System.out.println("Couldn't synchronize the directory: ");
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (SocketException e) {
            System.out.println("Couldn't connect the device: ");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        while (true) {
            String line = input.nextLine();
            String[] params = line.split(" ");

            if(params.length == 0)
                continue;

            String commandName = params[0];

            if(!commands.containsKey(commandName))
            {
                System.out.println("Command \"" + commandName +  "\" not found");
                continue;
            }

            Command command = commands.get(commandName);
            try {
                command.run(Arrays.copyOfRange(params, 1, params.length));
            } catch (Exception e) {
                System.out.println(commandName + ": " + e.getMessage());
            }
        }
    }
}
