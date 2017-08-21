package org.flybit.service;

import java.util.Optional;

import org.flybit.domain.Peer;

public interface PeerService extends EnityService<Peer, Long>{
    
    Iterable<Peer> findConnectedPublicPeers();
    
    long countConnectedPublicPeers();
    
    Iterable<Peer> findAvailableToConnect(long lastConnectAttemptInstant);
    
    Optional<Peer> findByHostAndPort(String host, int port);
    
    Iterable<Peer> findBlacklistedPeers();
    
    int unBlacklisting(String id);
    
    int resetConnectedPeers();
}
