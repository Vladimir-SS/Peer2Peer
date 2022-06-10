package console.commands;

public class ExitCommand extends Command{
    public ExitCommand() {
        super("exit", "");
    }

    @Override
    public void run(String[] arguments) throws Exception {
        app.disconnect();
        System.exit(0);
    }
}
