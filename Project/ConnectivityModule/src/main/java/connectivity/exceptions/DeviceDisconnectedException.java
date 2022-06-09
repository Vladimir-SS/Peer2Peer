package connectivity.exceptions;

/**
 * Custom named exception defining an Exception
 */
public class DeviceDisconnectedException extends Exception {
    /**
     * This method throws an exception with the text 'Peer with id: 'peerID'
     * disconnected'
     * 
     * @param peerId The ID of a client
     */
    public DeviceDisconnectedException(int peerId) {
        super("Peer with id: " + peerId + " disconnected.");
    }

    /**
     * This method throws an exception with the text 'Peer with ip: 'peerIP'
     * disconnected'
     * 
     * @param peerIP The IP address of a client
     */
    public DeviceDisconnectedException(String peerIP) {
        super("Peer with ip: " + peerIP + " disconnected.");
    }
}
