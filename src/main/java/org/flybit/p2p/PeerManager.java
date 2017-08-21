package org.flybit.p2p;

public interface PeerManager {

    void initSeeds();
    
    void discoverPeers();
    
    void unBlacklistingPeers();
}
