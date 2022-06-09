package connectivity.exceptions;

import java.io.IOException;

public class BroadcastFailedException extends IOException {
    public BroadcastFailedException(IOException e){
        super(e);
    }
}
