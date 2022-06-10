package console.commands;

import george.resident.exceptions.DeviceNotFound;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ActionCommand extends Command{
    protected ActionCommand(String name) {
        super(name, "<index> [<path>]");
    }

    abstract protected void action(int index, Path path) throws DeviceNotFound, IOException;

    @Override
    public void run(String[] arguments) throws Exception {
        if(arguments.length == 0 || arguments.length > 2)
            throw manual();

        int index = parseUnsigned(arguments[0]);
        String pathString = arguments.length == 2 ? arguments[1] : "";
        action(index, Paths.get(pathString));
    }
}
