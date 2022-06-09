package george.resident.exceptions;

import java.io.IOException;

/**
 * Exception is thrown when the peer folder can not be created
 */
public class BadSyncDirectory extends IOException {
    public BadSyncDirectory(IOException exception){
        super(exception);
    }
}
