package Connectivity.Exceptions;

import javax.net.ssl.SSLHandshakeException;

public class HandshakeException extends SSLHandshakeException {
    public HandshakeException(int peerID) {
        super("Handshake failed");
    }
}
