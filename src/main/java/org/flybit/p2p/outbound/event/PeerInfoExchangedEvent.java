
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.event.BaseEvent;
import org.flybit.p2p.message.PeerInfoMessage;

public class PeerInfoExchangedEvent extends BaseEvent {
    private static final long serialVersionUID = -7842163164024574892L;

    private final Peer peer;

    private final PeerInfoMessage peerInfoMessage;

    public PeerInfoExchangedEvent(Peer peer, PeerInfoMessage peerInfoMessage) {
        this.peer = peer;
        this.peerInfoMessage = peerInfoMessage;
    }

    public Peer getPeer() {
        return peer;
    }

    public PeerInfoMessage getPeerInfoMessage() {
        return peerInfoMessage;
    }

}
