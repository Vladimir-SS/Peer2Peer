package console.commands;

public class ManualException extends Exception {
    /*package-private*/ ManualException(Command command){
        super("manual: " + command.getName() + " " + command.getManual());
    }
}
