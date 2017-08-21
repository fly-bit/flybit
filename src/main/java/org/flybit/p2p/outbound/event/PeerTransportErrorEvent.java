
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.event.BaseEvent;

public class PeerTransportErrorEvent extends BaseEvent {
    private static final long serialVersionUID = -7842163164024574892L;

    private final Peer peer;

    private final Throwable exception;

    public PeerTransportErrorEvent(Peer peer, Throwable exception) {
        this.peer = peer;
        this.exception = exception;
    }

    public Peer getPeer() {
        return peer;
    }

    public Throwable getException() {
        return exception;
    }

}
