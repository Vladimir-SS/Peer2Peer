package console.commands;

/**
 * This class is used for implementing the Exit command : exit.
 */

public class ExitCommand extends Command{

    /**
     * This method is used to execute the exit command.
     * It closes the connection if it exists.
     * @param arguments
     */
    @Override
    public void run(String[] arguments) {

        try {
            if(app.getPeer() != null)
                app.getPeer().close();
        }catch (Exception ignored){}
        System.exit(0);
    }

    @Override
    public Exception manual() {
        return new Exception("exit");
    }
}
