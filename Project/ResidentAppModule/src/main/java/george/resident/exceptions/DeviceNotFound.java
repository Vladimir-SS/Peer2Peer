package george.resident.exceptions;

/**
 * Exception is thrown when the device is not found.
 */
public class DeviceNotFound extends Exception {
    public DeviceNotFound(){
        super("Device not found");
    }
}
