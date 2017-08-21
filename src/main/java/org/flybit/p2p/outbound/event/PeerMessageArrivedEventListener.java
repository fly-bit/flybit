
package org.flybit.p2p.outbound.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.PeerConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PeerMessageArrivedEventListener {

    private static final Log log = LogFactory.getLog(PeerMessageArrivedEventListener.class);
    
    @Autowired
    private PeerConnectionManager peerConnectionManager;

    @Async
    @EventListener
    public void onEvent(PeerMessageArrivedEvent event) {
        log.info("Message arrived "+event.getPayload());
        peerConnectionManager.notifyResponseArrived(event.getPeer(), event.getPayload());
    }

}
