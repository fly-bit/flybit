
package org.flybit.p2p.outbound;

import org.flybit.p2p.message.RequestMessage;

public interface PeerOubtoundRequestManager {
    long nextRequestId();
    
    PeerOutboundRequest requireOutboundRequest(RequestMessage requestMessage);
    
    void notifyResponse(Long requestId, Object response);
    
    void clearPeerOutboundRequests(String reason);
}
