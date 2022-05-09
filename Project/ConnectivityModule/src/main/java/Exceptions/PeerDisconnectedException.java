package Exceptions;

public class PeerDisconnectedException extends  Exception{
    public PeerDisconnectedException(int peerId) {
        super("Peer with id: " + peerId + " disconnected.");
    }
    public PeerDisconnectedException(String peerIP) {
        super("Peer with ip: " + peerIP + " disconnected.");
    }
}
