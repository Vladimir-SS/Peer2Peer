package connectivity.exceptions;

import java.net.BindException;

/**
 * Custom named exception implementing BindException
 */
public class PortException extends BindException {
    /**
     * This method throws an exception with the text 'Connection failed, port 'port' already in use'
     * @param port A port value
     */
    public PortException(int port) {
        super("Connection failed, port " + port + " already in use");
    }
}
