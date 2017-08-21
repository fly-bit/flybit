
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.event.BaseEvent;

public class PeerMessageArrivedEvent extends BaseEvent {
    private static final long serialVersionUID = -7842163164024574892L;

    private final Peer peer;

    private final Object payload;

    public PeerMessageArrivedEvent(Peer peer, Object payload) {
        this.peer = peer;
        this.payload = payload;
    }

    public Peer getPeer() {
        return peer;
    }

    public Object getPayload() {
        return payload;
    }

}
