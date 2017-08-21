
package org.flybit.p2p.outbound.event;

import org.flybit.p2p.PeerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PeerConnectRequestEventListener {

    @Autowired
    private PeerClient peerClient;

    @Async
    @EventListener
    public void onEvent(PeerConnectRequestEvent event) {
        peerClient.connect(event.getPeer());
    }

}
