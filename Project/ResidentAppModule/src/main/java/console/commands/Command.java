package console.commands;

import george.resident.sync.ConnectivityResident;

public abstract class Command {
    protected ConnectivityResident app;

    public void setApp(ConnectivityResident app) {
        this.app = app;
    }

    public abstract void run(String[] arguments) throws Exception;

    //TODO: perhaps a better way to do it...
    public abstract Exception manual();
}
