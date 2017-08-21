
package org.flybit.p2p.outbound.event;

import org.flybit.domain.Peer;
import org.flybit.p2p.PeerClient;
import org.flybit.p2p.exception.PeerException;
import org.flybit.p2p.message.PeerInfoMessage;
import org.flybit.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PeerInfoExchangeRequestEventListener {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private PeerService peerService;

    @Autowired
    private PeerClient peerClient;

    @Async
    @EventListener
    public void onEvent(PeerInfoExchangeRequestEvent event) {

        try {
            final PeerInfoMessage peerInfoMessage = peerClient.exchangePeerInfo(event.getPeer());
            if (peerInfoMessage != null) {
                final Peer peerInDb = peerService.findById(event.getPeer().getId()).get();
                peerInDb.setAnnouncedAddress(peerInfoMessage.getAnnouncedAddress());
                peerInDb.setApplication(peerInfoMessage.getApplication());
                peerInDb.setApplicationVersion(peerInfoMessage.getApplicationVersion());
                peerInDb.setPeerVersion(peerInfoMessage.getPeerVersion());
                peerInDb.setPlatform(peerInfoMessage.getPlatform());
                peerInDb.setPeerType(peerInfoMessage.getPeerType());
                peerInDb.setShare(peerInfoMessage.isShare());
                final Peer peer = peerService.save(peerInDb);

                applicationEventPublisher.publishEvent(new PeerInfoExchangedEvent(peer, peerInfoMessage));
            } else {
                applicationEventPublisher.publishEvent(new PeerInfoExchangeFailedEvent(event.getPeer()));
            }
        } catch (final PeerException pe) {
            applicationEventPublisher.publishEvent(new PeerInfoExchangeFailedEvent(event.getPeer()));
        }
    }

}
