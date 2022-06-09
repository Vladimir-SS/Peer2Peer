package console.commands;

import connectivity.Peer;
import george.ConnectivityResident;
import george.SynchronizedDirectory;

public abstract class Command {
    protected ConnectivityResident app = new ConnectivityResident();

    public void setApp(ConnectivityResident app) {
        this.app = app;
    }

    public abstract void run(String[] arguments) throws Exception;

    //TODO: perhaps a better way to do it...
    public abstract Exception manual();
}
