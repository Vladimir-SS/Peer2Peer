package console.commands;

public class ExitCommand extends Command{
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
