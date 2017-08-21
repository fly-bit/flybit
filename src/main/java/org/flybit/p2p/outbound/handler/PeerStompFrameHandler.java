
package org.flybit.p2p.outbound.handler;

import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.flybit.p2p.PeerClientImpl;
import org.flybit.p2p.outbound.event.PeerMessageArrivedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

public class PeerStompFrameHandler implements StompFrameHandler {

    private static final Log log = LogFactory.getLog(PeerClientImpl.class);

    private final Peer peer;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Type payloadType;

    public PeerStompFrameHandler(Peer peer, ApplicationEventPublisher applicationEventPublisher, Type payloadType) {
        this.peer = peer;
        this.applicationEventPublisher = applicationEventPublisher;
        this.payloadType = payloadType;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return payloadType;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.debug("Peer " + peer.getAnnouncedAddress() + " received message payload " + payload);
        applicationEventPublisher.publishEvent(new PeerMessageArrivedEvent(peer, payload));
    }

}
