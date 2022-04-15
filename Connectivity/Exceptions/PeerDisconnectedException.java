package Connectivity.Exceptions;

public class PeerDisconnectedException extends  Exception{
    public PeerDisconnectedException(int peerId) {
        super("Peer with id: " + peerId + " disconnected.");
    }
}
