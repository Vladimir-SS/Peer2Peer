import Commands.Command;
import Commands.DeviceCommand;
import Connectivity.Peer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleInterface {

    private static final Map<String, Command> commands = new HashMap<>(){{
        put("device", new DeviceCommand());
    }};

    private static Peer peer;


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("*** To start a connection enter a port ***:");
        int port = Integer.parseInt(input.nextLine());

        while (!Peer.isAvailable(port)) {
            System.out.println("The port " + port + " is already used. Enter a new port: ");
            port = Integer.parseInt(input.nextLine());
        }

        try {
            peer = new Peer(4444);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
                command.run(peer, Arrays.copyOfRange(params, 1, params.length));
            } catch (Exception e) {
                System.out.println(params[0] + ": " + e.getMessage());
            }
        }
    }
}
