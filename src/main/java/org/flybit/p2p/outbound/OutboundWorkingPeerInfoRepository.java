
package org.flybit.p2p.outbound;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.springframework.stereotype.Component;

@Component
public class OutboundWorkingPeerInfoRepository {

    private static final Log log = LogFactory.getLog(OutboundWorkingPeerInfoRepository.class);


    private final Map<String, Peer> workingPeerInfoMap = new ConcurrentHashMap<>();


    public Peer get(String key) {
        return workingPeerInfoMap.get(key);
    }

    public Peer put(String key, Peer peer) {
        return workingPeerInfoMap.put(key, peer);
    }

    public Peer remove(String key) {
        return workingPeerInfoMap.remove(key);
    }

    public Collection<Peer> values() {
        return workingPeerInfoMap.values();
    }
    
    public int size() {
        return workingPeerInfoMap.size();
    }

}
