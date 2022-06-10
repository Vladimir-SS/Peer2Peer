package console.commands;

import java.util.concurrent.atomic.AtomicInteger;

public class ConnectedCommand extends Command{

    public ConnectedCommand() {
        super("connected", "");
    }

    @Override
    public void run(String[] arguments) throws Exception {
        if(arguments.length > 0)
            throw manual();

        System.out.println("Connected Devices: ");
        AtomicInteger i = new AtomicInteger();
        app.getConnectedDevices().forEach(device -> System.out.println((i.getAndIncrement()) + ". " + device));
    }
}