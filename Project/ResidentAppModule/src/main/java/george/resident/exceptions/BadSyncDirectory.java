package george.resident.exceptions;

import java.io.IOException;

public class BadSyncDirectory extends IOException {
    public BadSyncDirectory(IOException exception){
        super(exception);
    }
}
