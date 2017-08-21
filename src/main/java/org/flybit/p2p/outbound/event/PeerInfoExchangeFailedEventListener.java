
package org.flybit.p2p.outbound.event;

import org.flybit.p2p.PeerConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PeerInfoExchangeFailedEventListener{

    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    @Async
    @EventListener
    public void onEvent(PeerInfoExchangeFailedEvent event) {

        peerConnectionManager.removeOutboundSession(event.getPeer());
    }
    
}
