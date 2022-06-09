package console.commands;

import george.resident.exceptions.DeviceNotFound;

import java.io.IOException;
import java.nio.file.Path;

public class DeleteCommand extends ActionCommand {
    public DeleteCommand() {
        super("delete");
    }

    @Override
    protected void action(int index, Path path) throws DeviceNotFound, IOException {
        app.deleteFiles(index, path);
    }
}
