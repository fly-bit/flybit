
package org.flybit.p2p.outbound;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.exception.PeerException;
import org.flybit.p2p.message.RequestMessage;

public class PeerOubtoundRequestManagerImpl implements PeerOubtoundRequestManager {

    private static final Log log = LogFactory.getLog(PeerOubtoundRequestManagerImpl.class);

    private final AtomicLong requestIdIndex = new AtomicLong();

    private final Map<Long, PeerOutboundRequest> peerOutboundRequestMap = new ConcurrentHashMap<>();

    @Override
    public long nextRequestId() {
        return requestIdIndex.incrementAndGet();
    }

    @Override
    public PeerOutboundRequest requireOutboundRequest(RequestMessage requestMessage) {
        final long requestId = nextRequestId();
        requestMessage.setRequestId(requestId);
        final PeerOutboundRequest peerOutboundRequest = new PeerOutboundRequest();
        peerOutboundRequestMap.put(requestId, peerOutboundRequest);
        
        return peerOutboundRequest;
    }

    @Override
    public void notifyResponse(Long requestId, Object response) {
        final PeerOutboundRequest peerOutboundRequest =peerOutboundRequestMap.remove(requestId);
        if(peerOutboundRequest!=null){
            peerOutboundRequest.complete(response);
        }
    }
    
    @Override
    public void clearPeerOutboundRequests(String reason){
        final PeerException pe = new PeerException(reason);
        final Set<Map.Entry<Long, PeerOutboundRequest>> requests = peerOutboundRequestMap.entrySet();
        requests.forEach((entry) -> entry.getValue().complete(pe));
        peerOutboundRequestMap.clear();
    }

}
