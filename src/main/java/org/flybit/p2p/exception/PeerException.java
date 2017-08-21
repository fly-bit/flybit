package org.flybit.p2p.exception;

public class PeerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PeerException() {
        super();
    }

    public PeerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PeerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PeerException(String message) {
        super(message);
    }

    public PeerException(Throwable cause) {
        super(cause);
    }

}
