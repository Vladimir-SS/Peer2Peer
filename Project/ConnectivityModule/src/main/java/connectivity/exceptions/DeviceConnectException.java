package connectivity.exceptions;

import java.io.IOException;

public class DeviceConnectException extends IOException {
    public DeviceConnectException(IOException e){
        super(e);
    }
}
