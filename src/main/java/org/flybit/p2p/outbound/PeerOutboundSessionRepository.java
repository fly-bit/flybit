
package org.flybit.p2p.outbound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;

@Component
public class PeerOutboundSessionRepository {

    private static final Log log = LogFactory.getLog(PeerOutboundSessionRepository.class);


    private final Map<String, StompSession> outboundSessionMap = new ConcurrentHashMap<>();

    public StompSession get(String key) {
        return outboundSessionMap.get(key);
    }

    public StompSession put(String key, StompSession session) {
        return outboundSessionMap.put(key, session);
    }

    public int size() {
        return outboundSessionMap.size();
    }

    public StompSession remove(String key) {
        return outboundSessionMap.remove(key);
    }


}
