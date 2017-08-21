
package org.flybit.p2p.outbound.handler;

import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.flybit.p2p.PeerClientImpl;
import org.flybit.p2p.outbound.event.PeerConnectedEvent;
import org.flybit.p2p.outbound.event.PeerTransportErrorEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class PeerStompSessionHandler extends StompSessionHandlerAdapter implements StompSessionHandler {

    private static final Log log = LogFactory.getLog(PeerClientImpl.class);

    private final Peer peer;

    private final ApplicationEventPublisher applicationEventPublisher;

    public PeerStompSessionHandler(Peer peer, ApplicationEventPublisher applicationEventPublisher) {
        this.peer = peer;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        log.info("handleFrame  " + payload);

    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        final PeerConnectedEvent event = new PeerConnectedEvent(peer, session);
        applicationEventPublisher.publishEvent(event);
        log.info("Connected to peer " + peer.getAnnouncedAddress());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
            Throwable exception) {
        log.info("handleException  " + exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.info("handleTransportError  " + exception);
        final PeerTransportErrorEvent event = new PeerTransportErrorEvent(peer, exception);
        applicationEventPublisher.publishEvent(event);
    }

}
