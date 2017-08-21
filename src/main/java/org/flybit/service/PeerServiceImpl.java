package org.flybit.service;

import java.util.Optional;

import org.flybit.domain.Peer;
import org.flybit.repository.PeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PeerServiceImpl extends BaseEntityService<Peer, Long> implements PeerService {
    @Autowired
    private PeerRepository repository;

    @Override
    public CrudRepository<Peer, Long> getRepository() {
        return repository;
    }
    
    @Override
    public Iterable<Peer> findConnectedPublicPeers(){
        return repository.findConnectedPublicPeers();
    }

    @Override
    public long countConnectedPublicPeers() {
        return repository.countConnectedPublicPeers();
    }

    @Override
    public Iterable<Peer> findAvailableToConnect(long lastConnectAttemptInstant) {
        return repository.findAvailableToConnect(lastConnectAttemptInstant);
    }

    @Override
    public Optional<Peer> findByHostAndPort(String host, int port) {
        return repository.findByHostAndPort(host, port);
    }

    @Override
    public Iterable<Peer> findBlacklistedPeers() {
        return repository.findBlacklistedPeers();
    }

    @Override
    public int unBlacklisting(String id) {
        return repository.unBlacklisting(id);
    }

    @Override
    public int resetConnectedPeers() {
        return repository.resetConnectedPeers();
    }
    
    
}
