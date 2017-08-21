
package org.flybit.p2p.outbound.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.PeerConnectionManager;
import org.flybit.p2p.outbound.PeerOubtoundRequestManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PeerInfoExchangedEventListener{

    private static final Log log = LogFactory.getLog(PeerOubtoundRequestManagerImpl.class);

    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    @Async
    @EventListener
    public void onEvent(PeerInfoExchangedEvent event) {
        log.debug("PeerInfoExchangedEvent");
        peerConnectionManager.registerWorkingPeer(event.getPeer().getId().toString(), event.getPeer());
        
    }
    
}
