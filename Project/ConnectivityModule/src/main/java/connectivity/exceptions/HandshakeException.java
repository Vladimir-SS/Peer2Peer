package connectivity.exceptions;

import javax.net.ssl.SSLHandshakeException;

/**
 * Custom exception implementing SSLHandshakeException
 */
public class HandshakeException extends SSLHandshakeException {
    /**
     * The method returns an exception with the text 'Handshake failed'
     * @param peerID The ID of a client which fails to connect
     */
    public HandshakeException(int peerID) {
        super("Handshake failed");
    }
}
