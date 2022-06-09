package console.commands;

import connectivity.connection.Connection;

import java.util.List;

public class SyncCommand extends Command {
    @Override
    public void run(String[] arguments) throws Exception {
        if(arguments.length != 1)
            throw manual();

        int index = Integer.parseInt(arguments[0]);

        List<Connection> lastSearch = app.getLastSearch();

        if(index < 0 || index >= lastSearch.size())
            throw new Exception("index " + index + " not found");

        app.syncFiles(lastSearch.get(index));
    }

    @Override
    public Exception manual() {
        return new Exception("sync <index>");
    }
}
