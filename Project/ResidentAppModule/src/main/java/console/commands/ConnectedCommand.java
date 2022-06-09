package console.commands;

public class ConnectedCommand extends Command{

    protected ConnectedCommand() {
        super("connected", "");
    }

    @Override
    public void run(String[] arguments) throws Exception {
        if(arguments.length > 0)
            throw manual();


    }
}
