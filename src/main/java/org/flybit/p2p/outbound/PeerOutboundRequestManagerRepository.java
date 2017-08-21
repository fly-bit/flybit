
package org.flybit.p2p.outbound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class PeerOutboundRequestManagerRepository {

    private static final Log log = LogFactory.getLog(PeerOutboundRequestManagerRepository.class);

    private final Map<String, PeerOubtoundRequestManager> outboundRequestManagerMap = new ConcurrentHashMap<>();

    public PeerOubtoundRequestManager get(String key) {
        return outboundRequestManagerMap.get(key);
    }

    public PeerOubtoundRequestManager put(String key, PeerOubtoundRequestManager peerOubtoundRequestManager) {
        return outboundRequestManagerMap.put(key, peerOubtoundRequestManager);
    }

    public int size() {
        return outboundRequestManagerMap.size();
    }

    public PeerOubtoundRequestManager remove(String key) {
        return outboundRequestManagerMap.remove(key);
    }

}
