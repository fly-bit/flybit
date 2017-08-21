package org.flybit.p2p.message;

import java.util.Collection;

public class PeerInfoListMessage extends RequestMessage{

    private Collection<PeerInfoMessage> peerInfoMessages;

    public Collection<PeerInfoMessage> getPeerInfoMessages() {
        return peerInfoMessages;
    }

    public void setPeerInfoMessages(Collection<PeerInfoMessage> peerInfoMessages) {
        this.peerInfoMessages = peerInfoMessages;
    }
    

}
