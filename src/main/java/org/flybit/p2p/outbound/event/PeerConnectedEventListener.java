
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.domain.PeerState;
import org.flybit.p2p.PeerConnectionManager;
import org.flybit.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PeerConnectedEventListener{

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    @Autowired
    private PeerConnectionManager peerConnectionManager;

    @Autowired
    private PeerService peerService;
    
    @Async
    @EventListener
    public void onEvent(PeerConnectedEvent event) {
        final Peer peerInDb = peerService.findById(event.getPeer().getId()).get();
        peerInDb.setLastConnectedInstant(System.currentTimeMillis());
        peerInDb.setPeerState(PeerState.CONNECTED);
        peerService.save(peerInDb);
        
        final StompSession stompSession = event.getStompSession();
        peerConnectionManager.initOutboundSession(event.getPeer(), stompSession);
        
        final PeerInfoExchangeRequestEvent exchangePeerInfoRequestEvent = new PeerInfoExchangeRequestEvent(event.getPeer());
        applicationEventPublisher.publishEvent(exchangePeerInfoRequestEvent);
    }
    
}
