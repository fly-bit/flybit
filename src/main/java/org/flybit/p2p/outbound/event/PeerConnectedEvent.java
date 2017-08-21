
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.event.BaseEvent;
import org.springframework.messaging.simp.stomp.StompSession;

public class PeerConnectedEvent extends BaseEvent {
    private static final long serialVersionUID = -7842163164024574892L;

    private final Peer peer;

    private final StompSession stompSession;

    public PeerConnectedEvent(Peer peer, StompSession stompSession) {
        this.peer = peer;
        this.stompSession = stompSession;
    }

    public Peer getPeer() {
        return peer;
    }

    public StompSession getStompSession() {
        return stompSession;
    }

}
