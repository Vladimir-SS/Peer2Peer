package connectivity.exceptions;

import java.net.BindException;

public class PortException extends BindException {
    public PortException(int port) {
        super("Connection failed, port " + port + " already in use");
    }
}
