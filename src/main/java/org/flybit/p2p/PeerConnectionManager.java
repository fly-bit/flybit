
package org.flybit.p2p;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.flybit.domain.Peer;
import org.flybit.p2p.message.RequestMessage;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.WebSocketSession;

public interface PeerConnectionManager {

    StompSession getOutboundSession(Peer peer);

    StompSession registerOutboundSession(Peer peer, StompSession stompSession);

    void initOutboundSession(Peer peer, StompSession stompSession);

    StompSession removeOutboundSession(Peer peer);
    
    void sendAndForget(Peer peer, String destination, Object payload);
    
    Object sendForResponse(Peer peer, String destination, RequestMessage requestMessage);
    
    Object sendForResponse(Peer peer, String destination, RequestMessage requestMessage,long timeout, TimeUnit unit);
    
    void notifyResponseArrived(Peer peer, Object response);
    
    Peer registerWorkingPeer(String peerId, Peer peer);
    
    Collection<Peer> getWorkingPeers();
    
    Peer getRandomWorkingPeer();
    
    WebSocketSession getInboundSession(String sessionId);

    WebSocketSession registerInboundSession(String ip, WebSocketSession webSocketSession);
    
    Collection<WebSocketSession> getInboundSessionByIp(String ip);
    
    void closeIpSessions(String ip);
    
    boolean reachMaxInboundConnections();
    
    boolean reachMaxOutboundConnections();
}
