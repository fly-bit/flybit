
package org.flybit.p2p;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.flybit.p2p.outbound.event.PeerConnectRequestEvent;
import org.flybit.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;

@Component
public class PeerConnectorImpl implements PeerConnector {
    private static final Log log = LogFactory.getLog(PeerConnectorImpl.class);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private PeerService peerService;

    @Autowired
    private PeerConnectionManager peerConnectionManager;

    @Override
    public void connectToSomePeers() {

        try {

            if (!peerConnectionManager.reachMaxOutboundConnections()) {

                final Iterable<Peer> availablePeers = peerService
                        .findAvailableToConnect(System.currentTimeMillis() - 600000);
                log.debug("Available peers to connect "+ availablePeers);
                if(Iterables.isEmpty(availablePeers)){
                    return;
                }
                
                final Peer[] peersArray = Iterables.toArray(availablePeers, Peer.class);
                final Set<Peer> trySet = new HashSet<>();
                for (int i = 0; i < 6; i++) {
                    trySet.add(peersArray[(ThreadLocalRandom.current().nextInt(peersArray.length))]);
                }
                log.debug("Peers to try to connect "+ trySet);
                trySet.forEach(peer -> {
                    final PeerConnectRequestEvent peerConnectRequestEvent = new PeerConnectRequestEvent(peer);
                    applicationEventPublisher.publishEvent(peerConnectRequestEvent);

                });
            }

        } catch (final Exception e) {
            log.error("Error connecting to peer", e);
        }

    }

}
