package org.flybit.repository;

import java.util.Optional;

import org.flybit.domain.Peer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PeerRepository extends CrudRepository<Peer, Long>{
    
  @Query("SELECT p FROM Peer p WHERE p.isBlacklisted = false AND p.peerState = org.flybit.domain.PeerState.CONNECTED AND p.announcedAddress !=null ")
  Iterable<Peer> findConnectedPublicPeers();
  
  
  @Query("SELECT COUNT(p) FROM Peer p WHERE p.isBlacklisted = false AND p.peerState = org.flybit.domain.PeerState.CONNECTED AND p.announcedAddress !=null ")
  long countConnectedPublicPeers();
  
  
  @Query("SELECT p FROM Peer p WHERE p.isBlacklisted = false AND p.peerState != org.flybit.domain.PeerState.CONNECTED AND p.announcedAddress !=null AND p.lastConnectInstant < :lastConnectInstant")
  Iterable<Peer> findAvailableToConnect(@Param("lastConnectInstant") long lastConnectInstant);
  
  @Query
  Optional<Peer> findByHostAndPort(String host, int port);
  
  @Query("SELECT p FROM Peer p WHERE p.isBlacklisted = true")
  Iterable<Peer> findBlacklistedPeers();
  
  
  @Modifying @Query("update Peer p set p.isBlacklisted = false WHERE p.id = :id") 
  int unBlacklisting(@Param("id") String id);
  
  @Modifying @Query("update Peer p set p.peerState = org.flybit.domain.PeerState.NON_CONNECTED WHERE p.peerState = org.flybit.domain.PeerState.CONNECTED") 
  int resetConnectedPeers();
  
}
