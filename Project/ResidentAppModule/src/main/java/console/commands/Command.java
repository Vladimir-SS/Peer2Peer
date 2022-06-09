package console.commands;

import george.resident.sync.ConnectivityResident;

import java.nio.file.Paths;


public abstract class Command {
    protected ConnectivityResident app;
    protected final String name;
    protected final String manual;

    protected Command(String name, String manual) {
        this.name = name;
        this.manual = manual;
    }

    public String getName() {
        return name;
    }

    public String getManual() {
        return manual;
    }

    protected static int parseUnsigned(String s) throws IllegalArgumentException {
        try {
            int x = Integer.parseInt(s);
            if(x < 0)
                throw new IllegalArgumentException("The argument needs to be a Natural Number");
            return x;
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("The argument needs to be a Integer");
        }
    }

    public void setApp(ConnectivityResident app) {
        this.app = app;
    }

    public abstract void run(String[] arguments) throws Exception;

    public ManualException manual() {
        return new ManualException(this);
    }
}
