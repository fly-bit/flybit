package org.flybit.p2p;

import org.flybit.domain.Peer;
import org.flybit.p2p.message.PeerInfoListMessage;
import org.flybit.p2p.message.PeerInfoMessage;

public interface PeerClient {

    void connect(Peer peer);
    
    PeerInfoMessage exchangePeerInfo(Peer peer);
    
    PeerInfoListMessage getWorkingPeers(Peer peer);
}
