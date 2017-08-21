
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.domain.PeerState;
import org.flybit.p2p.PeerConnectionManager;
import org.flybit.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PeerTransportErrorEventListener {

    @Autowired
    private PeerService peerService;

    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    @Async
    @EventListener
    public void onEvent(PeerTransportErrorEvent event) {
        peerConnectionManager.removeOutboundSession(event.getPeer());
        final long now = System.currentTimeMillis();
        final Peer peerInDb = peerService.findById(event.getPeer().getId()).get();
        
        if (event.getException() instanceof ConnectionLostException) {
            peerInDb.setLastDisconnectedInstant(System.currentTimeMillis());
            peerInDb.setPeerState(PeerState.DISCONNECTED);
            peerService.save(peerInDb);
        }else{
            peerInDb.setLastDisconnectedInstant(now);
            peerInDb.setPeerState(PeerState.DISCONNECTED);
            peerInDb.setBlacklisted(true);
            peerInDb.setLastBlacklistedInstant(now);
            peerService.save(peerInDb);
            
        }
    }

}
