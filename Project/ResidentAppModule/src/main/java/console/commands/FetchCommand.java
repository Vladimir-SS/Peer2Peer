package console.commands;

import george.resident.exceptions.DeviceNotFound;

import java.io.IOException;
import java.nio.file.Path;

public class FetchCommand extends ActionCommand {
    public FetchCommand() {
        super("fetch");
    }

    @Override
    protected void action(int index, Path path) throws DeviceNotFound, IOException {
        app.fetchFiles(index, path);
    }
}
