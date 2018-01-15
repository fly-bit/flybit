
package org.flybit.p2p;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.flybit.p2p.inbound.PeerInboundSessionRepository;
import org.flybit.p2p.message.PeerInfoListMessage;
import org.flybit.p2p.message.PeerInfoMessage;
import org.flybit.p2p.message.RequestMessage;
import org.flybit.p2p.outbound.OutboundWorkingPeerInfoRepository;
import org.flybit.p2p.outbound.PeerOubtoundRequestManager;
import org.flybit.p2p.outbound.PeerOubtoundRequestManagerImpl;
import org.flybit.p2p.outbound.PeerOutboundRequest;
import org.flybit.p2p.outbound.PeerOutboundRequestManagerRepository;
import org.flybit.p2p.outbound.PeerOutboundSessionRepository;
import org.flybit.p2p.outbound.handler.PeerStompFrameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

@Component
public class PeerConnectionManagerImpl implements PeerConnectionManager {
    private static final Log log = LogFactory.getLog(PeerConnectionManagerImpl.class);

    private static final CloseStatus IP_BLACKLISTED_STATUS = new CloseStatus(CloseStatus.POLICY_VIOLATION.getCode(),
            "This connection was established by ip that is blacklisted");
    
    @Autowired
    private PeerInboundSessionRepository peerInboundSessionRepository;
    
    @Autowired
    private PeerOutboundSessionRepository peerOutboundSessionRepository;

    @Autowired
    private PeerOutboundRequestManagerRepository peerOutboundRequestManagerRepository;
    
    
    @Autowired
    private OutboundWorkingPeerInfoRepository workingPeerInfoRepository;
    
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
    @Value("${flybit.peer.response-read-timeout:20000}")
    private int responseReadTimeout;

    @Value("${flybit.peer.max-inbound-connections:250}")
    private int maxInboundConnections;

    @Value("${flybit.peer.max-outbound-connections:50}")
    private int maxOutboundConnections;
    
    @Override
    public StompSession getOutboundSession(Peer peer){
        return peerOutboundSessionRepository.get(peer.getId().toString());
    }
    
    @Override
    public StompSession registerOutboundSession(Peer peer, StompSession stompSession){
        return peerOutboundSessionRepository.put(peer.getId().toString(), stompSession);
    }
    
    @Override
    public StompSession removeOutboundSession(Peer peer) {
        workingPeerInfoRepository.remove(peer.getId().toString());
        final PeerOubtoundRequestManager peerOubtoundRequestManager =peerOutboundRequestManagerRepository.remove(peer.getId().toString());
        if(peerOubtoundRequestManager!=null){
            peerOubtoundRequestManager.clearPeerOutboundRequests("Peer session is removed");
        }
        return peerOutboundSessionRepository.remove(peer.getId().toString());
        
    }
    
    @Override
    public void initOutboundSession(Peer peer, StompSession stompSession) {
        
        stompSession.subscribe("/peer/queue/peerinfo", new PeerStompFrameHandler(peer, applicationEventPublisher, PeerInfoMessage.class));
        stompSession.subscribe("/peer/queue/workingpeerinfo", new PeerStompFrameHandler(peer, applicationEventPublisher, PeerInfoListMessage.class));
        
        peerOutboundRequestManagerRepository.put(peer.getId().toString(), new PeerOubtoundRequestManagerImpl());
        registerOutboundSession(peer,stompSession);
        log.debug(String.format("StompSession %s initialized for peer %s", stompSession.getSessionId(), peer.getId()));
        final PeerOubtoundRequestManager peerOubtoundRequestManager= peerOutboundRequestManagerRepository.get(peer.getId().toString());
        log.debug("Put peerOubtoundRequestManager " + peerOubtoundRequestManager);
    }

    @Override
    public void sendAndForget(Peer peer, String destination, Object payload) {
        final StompSession stompSession = getOutboundSession(peer);
        if(stompSession!=null){
            stompSession.send(destination, payload);
        }
    }

    @Override
    public Object sendForResponse(Peer peer, String destination, RequestMessage requestMessage) {
        return sendForResponse(peer, destination, requestMessage, responseReadTimeout, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public Object sendForResponse(Peer peer, String destination, RequestMessage requestMessage,long timeout, TimeUnit unit) {
        log.debug("sendForResponse for peer " + peer.getId());
        final StompSession stompSession = getOutboundSession(peer);
        if(stompSession!=null){
            synchronized(stompSession){
                final boolean connected = stompSession.isConnected();
                final PeerOubtoundRequestManager peerOubtoundRequestManager= peerOutboundRequestManagerRepository.get(peer.getId().toString());
                log.debug("Get peerOubtoundRequestManager " + peerOubtoundRequestManager);
                final PeerOutboundRequest peerOutboundRequest = peerOubtoundRequestManager.requireOutboundRequest(requestMessage);
                log.debug("Session connected is:"+connected);
                stompSession.send(destination, requestMessage);
                final Object response = peerOutboundRequest.get(timeout, unit);
                return response;
            }
        }
        log.error("Session does not exit for peer "+ peer.getAnnouncedAddress());
        return null;

    }

    @Override
    public void notifyResponseArrived(Peer peer, Object response) {
        if(response instanceof RequestMessage){
            final Long requestId = ((RequestMessage)response).getRequestId();
            final PeerOubtoundRequestManager peerOubtoundRequestManager = peerOutboundRequestManagerRepository.get(peer.getId().toString());
            log.debug("response arrived for request id "+ requestId);
            if(peerOubtoundRequestManager!=null){
                peerOubtoundRequestManager.notifyResponse(requestId, response);
            }
        }
        
    }

    @Override
    public Peer registerWorkingPeer(String peerId, Peer peer) {
        return workingPeerInfoRepository.put(peerId, peer);
    }
    
    @Override
    public Collection<Peer> getWorkingPeers() {
        return workingPeerInfoRepository.values();
    }

    @Override
    public Peer getRandomWorkingPeer(){
        final Collection<Peer> workingPeers = this.getWorkingPeers();
        log.debug("Working peers "+ workingPeers);
        if(workingPeers.isEmpty()){
            return null;
        }
        
        workingPeers.forEach(peer-> log.debug("working peer "+ peer.getId()));
        final Peer selectedPeer=(Peer)workingPeers.toArray()[ThreadLocalRandom.current().nextInt(workingPeers.size())];
        log.debug("Selected peer "+ selectedPeer);
        return selectedPeer;
        
    }
    
    @Override
    public WebSocketSession getInboundSession(String sessionId) {
        return peerInboundSessionRepository.get(sessionId);
    }

    @Override
    public WebSocketSession registerInboundSession(String ip, WebSocketSession webSocketSession) {
        peerInboundSessionRepository.registerIpSession(ip, webSocketSession);
        return peerInboundSessionRepository.put(webSocketSession.getId(), webSocketSession);
    }

    @Override
    public Collection<WebSocketSession> getInboundSessionByIp(String ip) {
        return peerInboundSessionRepository.getInboundSessionByIp(ip);
    }
    

    @Override
    public void closeIpSessions(String ip) {
        if (log.isDebugEnabled()) {
            log.debug("Closing WebSocket connections associated to ip " + ip);
        }
        final Collection<WebSocketSession> sessions = getInboundSessionByIp(ip);
        
        for (final WebSocketSession toClose : sessions) {
            try {
                toClose.close(IP_BLACKLISTED_STATUS);
            } catch (final IOException e) {
                log.debug("Failed to close WebSocketSession (this is nothing to worry about but for debugging only)",
                        e);
            }
        }
    }
    
    @Override
    public boolean reachMaxInboundConnections() {
        return peerInboundSessionRepository.size() > maxInboundConnections;
    }

    @Override
    public boolean reachMaxOutboundConnections() {
        return peerOutboundSessionRepository.size() > maxOutboundConnections;
    }

    
}


