package console.commands;

import george.resident.sync.Device;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FindCommand extends Command{
    public FindCommand() {
        super("find", "find");
    }

    @Override
    public void run(String[] arguments) throws Exception {
        if(arguments.length != 0)
            throw manual();

        System.out.println("Searching devices...");
        List<Device> foundDevices =  app.findNewDevices();
        AtomicInteger index = new AtomicInteger();

        if(foundDevices.size() == 0)
            System.out.println("No devices found");
        else
            foundDevices.forEach(device -> System.out.println(index.getAndIncrement() + ". " + device));
    }
}
