
package org.flybit.p2p.inbound;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class PeerInboundSessionRepository {

    private static final Log log = LogFactory.getLog(PeerInboundSessionRepository.class);

    private final Map<String, WebSocketSession> inboundSessionMap = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Map<String, WebSocketSession>> ipToWsSessionMap = new ConcurrentHashMap<>();

    public WebSocketSession get(String key) {
        return inboundSessionMap.get(key);
    }

    public WebSocketSession put(String key, WebSocketSession session) {
        return inboundSessionMap.put(key, session);
    }

    public WebSocketSession remove(String key) {
        return inboundSessionMap.remove(key);
    }

    public int size() {
        return inboundSessionMap.size();
    }

    public void registerIpSession(String ip, WebSocketSession wsSession) {
        Map<String, WebSocketSession> sessionMap = this.ipToWsSessionMap.get(ip);
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<>();
            this.ipToWsSessionMap.putIfAbsent(ip, sessionMap);
            sessionMap = this.ipToWsSessionMap.get(ip);
        }
        sessionMap.put(wsSession.getId(), wsSession);
    }

    public Collection<WebSocketSession> getInboundSessionByIp(String ip) {
        final Map<String, WebSocketSession> sessionMap = this.ipToWsSessionMap.get(ip);
        if (sessionMap != null) {
            return sessionMap.values();
        }
        return  Collections.<WebSocketSession>emptyList();
    }
    

}
