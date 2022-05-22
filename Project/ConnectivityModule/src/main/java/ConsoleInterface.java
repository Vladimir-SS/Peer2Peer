import Commands.Command;
import Commands.DeviceCommand;
import Connectivity.Peer;

import java.io.IOException;
import java.util.*;

public class ConsoleInterface {

    private static final Map<String, Command> commands = new HashMap<>(){{
        put("device", new DeviceCommand());
    }};

    private static Peer peer;


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("*** To start a connection enter a port ***:");
            int port = Integer.parseInt(input.nextLine());
            System.out.println("Port: " + port);

            try {
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



        while (true) {
            String line = input.nextLine();
            String[] params = line.split(" ");

            if(params.length == 0)
                continue;

            if(Objects.equals(params[0], "exit"))
                break;

            if(!commands.containsKey(params[0]))
            {
                System.out.println("Commands not found");
                continue;
            }

            Command command = commands.get(params[0]);
            try {
                command.run(peer, Arrays.copyOfRange(params, 1, params.length));
            } catch (Exception e) {
                System.out.println(params[0] + ": " + e.getMessage());
            }
        }

        try {
            peer.close();
        } catch (IOException e) {
            System.out.println("Couldn't close program: " + e.getMessage());
        }
    }
}
