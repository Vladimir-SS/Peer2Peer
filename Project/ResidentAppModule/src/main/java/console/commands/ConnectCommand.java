package console.commands;

import java.util.concurrent.atomic.AtomicInteger;

public class ConnectCommand extends Command{

    public ConnectCommand() {
        super("connect", "<index>");
    }

    @Override
    public void run(String[] arguments) throws Exception {
        if(arguments.length > 1)
            throw manual();

        int index = parseUnsigned(arguments[0]);
        app.connectTo(index);


        System.out.println("Connected Devices: ");
        AtomicInteger i = new AtomicInteger();
        app.getConnectedDevices().forEach(device -> System.out.println((i.getAndIncrement()) + ". " + device));
        System.out.println();
    }
}
