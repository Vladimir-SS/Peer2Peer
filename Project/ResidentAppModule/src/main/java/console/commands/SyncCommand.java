package console.commands;

import connectivity.connection.Connection;

import java.util.List;

/**
 * This class is used for implementing the Sync command : sync 'index'.
 */

public class SyncCommand extends Command {

    /**
     * This method is used to execute the sync command.
     * @param arguments The only argument which must consist of the index
     *                  for which syncFiles will be invoked.
     * @throws Exception
     */
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

    /**
     * The function responsible for displaying the correct format for the Sync command.
     * Will be invoked each time an incorrect command is sent.
     * @return Exception
     */
    @Override
    public Exception manual() {
        return new Exception("sync <index>");
    }
}
