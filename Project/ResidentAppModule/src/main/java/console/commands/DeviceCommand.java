package console.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is used for implementing the Device commands :
 *      (--find | -f) | (--connect-to <index> | -ct <index>) | (--connected | -c).
 */

public class DeviceCommand extends Command{

    List<InetAddress> lastSearch = new ArrayList<>();

    /**
     * This method is used to search for connected devices.
     * @param arguments Must contain only the name of the command.
     * @throws Exception
     */
    public void findDevices(String[] arguments) throws Exception{

        if(arguments.length != 1)
            throw manual();

        try {
            System.out.println("Searching devices...");
            lastSearch = new ArrayList<>(app.getPeer().findDevices(false));
            AtomicInteger index = new AtomicInteger();

            if(lastSearch.size() == 0)
                System.out.println("No devices found");
            else
                lastSearch.forEach(device -> System.out.println(index.getAndIncrement() + ": " + device));
        } catch (IOException e) {
            throw new Exception("Broadcast Error");
        }
    }

    /**
     * This method is used to connect to the device found at the index
     * given as the second argument.
     * @param arguments Must contain an additional argument besides the command name.
     * @throws Exception
     */
    public void connectTo(String[] arguments) throws Exception{

        if(arguments.length != 2)
            throw manual();

        int index = Integer.parseInt(arguments[1]);

        if(index < 0 || index >= lastSearch.size())
            throw new Exception("index " + index + " not found");

        app.getPeer().connectDevice(lastSearch.get(index));
    }

    /**
     * This method checks which type of command related to devices has been sent.
     * The methods responsible for executing the commands will be invoked.
     * @param arguments Must contain at least one element (the command name).
     * @throws Exception
     */
    @Override
    public void run(String[] arguments) throws Exception {

        if(arguments.length == 0)
            throw manual();

        switch (arguments[0]) {
            case "--find", "-f" -> findDevices(arguments);
            case "--connect-to", "-ct" -> connectTo(arguments);
            case "--connected", "-c" -> connectedDevices(arguments);
            default -> throw manual();
        }

    }

    /**
     * This method displays all connected devices.
     * @param arguments Must contain only the name of the command.
     * @throws Exception
     */
    private void connectedDevices(String[] arguments) throws Exception{
        if(arguments.length != 1)
            throw manual();
        AtomicInteger index = new AtomicInteger();
        app.getConnectedDevices().forEach(device -> System.out.println(index.getAndIncrement() + ": " + device));
    }

    /**
     * The function responsible for displaying the correct possible commands.
     * Will be invoked each time an incorrect command is sent.
     * @return Exception
     */
    @Override
    public Exception manual() {
        return new Exception("(--find | -f) | (--connect-to <index> | -ct <index>) | (--connected | -c)");
    }
}
