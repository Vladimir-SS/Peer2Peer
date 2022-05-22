package Commands;

import Connectivity.Peer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DeviceCommand implements Command{

    List<InetAddress> lastSearch = new ArrayList<>();

    public void findDevices(Peer peer, String[] arguments) throws Exception{

        if(arguments.length != 1)
            throw manual();

        try {
            System.out.println("Searching devices...");
            lastSearch = new ArrayList<>(peer.findDevices(false));
            AtomicInteger index = new AtomicInteger();

            if(lastSearch.size() == 0)
                System.out.println("No devices found");
            else
                lastSearch.forEach(device -> System.out.println(index.getAndIncrement() + ": " + device));
        } catch (IOException e) {
            throw new Exception("Broadcast Error");
        }
    }

    public void connectTo(Peer peer, String[] arguments) throws Exception{

        if(arguments.length != 2)
            throw manual();

        int index = Integer.parseInt(arguments[1]);

        if(index < 0 || index >= lastSearch.size())
            throw new Exception("index " + index + " not found");

        peer.connectDevice(lastSearch.get(index));
    }

    @Override
    public void run(Peer peer, String[] arguments) throws Exception {

        if(arguments.length == 0)
            throw manual();

        switch (arguments[0]) {
            case "--find", "-f" -> findDevices(peer, arguments);
            case "--connect-to", "-ct" -> connectTo(peer, arguments);
            case "--connected", "-c" -> connectedDevices(peer, arguments);
            default -> throw manual();
        }

    }

    private void connectedDevices(Peer peer, String[] arguments) throws Exception{
        if(arguments.length != 1)
            throw manual();
        AtomicInteger index = new AtomicInteger();
        peer.getConnectedDevices().forEach(device -> System.out.println(index.getAndIncrement() + ": " + device));;
    }

    @Override
    public Exception manual() {
        return new Exception("(--get-all | -g) | (--connect-to <index> | -ct <index>) | (--connected | -c)");
    }
}
