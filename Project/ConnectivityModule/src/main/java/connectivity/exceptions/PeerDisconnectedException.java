package connectivity.exceptions;

/**
 * Custom named exception defining an Exception
 */
public class PeerDisconnectedException extends Exception{
    /**
     * This method throws an exception with the text 'Peer with id: 'peerID' disconnected'
     * @param peerId The ID of a client
     */
    public PeerDisconnectedException(int peerId) {
        super("Peer with id: " + peerId + " disconnected.");
    }
    /**
     * This method throws an exception with the text 'Peer with ip: 'peerIP' disconnected'
     * @param peerIP The IP address of a client
     */
    public PeerDisconnectedException(String peerIP) {
        super("Peer with ip: " + peerIP + " disconnected.");
    }
}
