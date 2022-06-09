package connectivity.exceptions;

public class DeviceDisconnectedException extends  Exception{
    public DeviceDisconnectedException(int peerId) {
        super("Peer with id: " + peerId + " disconnected.");
    }
    public DeviceDisconnectedException(String peerIP) {
        super("Peer with ip: " + peerIP + " disconnected.");
    }
}
