
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.event.BaseEvent;

public class PeerConnectRequestEvent extends BaseEvent {
    private static final long serialVersionUID = -7842163164024574892L;

    private final Peer peer;

    public PeerConnectRequestEvent(Peer peer) {
        this.peer = peer;
    }

    public Peer getPeer() {
        return peer;
    }
    
    
}
