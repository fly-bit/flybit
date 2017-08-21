
package org.flybit.p2p;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.flybit.domain.PeerType;
import org.flybit.p2p.message.PeerInfoListMessage;
import org.flybit.p2p.message.PeerInfoMessage;
import org.flybit.service.PeerService;
import org.flybit.util.PeerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class PeerManagerImpl implements PeerManager {
    private static final Log log = LogFactory.getLog(PeerManagerImpl.class);

    @Value("${flybit.offline-mode:false}")
    private boolean offlineMode;

    @Value("${flybit.my-address:}")
    private String myAddress;

    @Value("${flybit.peer.discover-new-peer:true}")
    private boolean discoverNewPeer;

    @Value("${flybit.peer.persist-discovered-peer:true}")
    private boolean persistDiscoveredPeer;

    @Value("${flybit.peer.reset-peers-in-db:false}")
    private boolean resetPeersInDb;

    @Value("#{'${flybit.peer.default-seeds:}'.split(',')}")
    private List<String> defaultSeeds;

    @Value("#{'${flybit.peer.well-known-peers:}'.split(',')}")
    private List<String> wellKnonwPeers;

    @Value("${flybit.peer.blacklist:}")
    private List<String> blacklist;

    @Value("${flybit.peer.max-known:2000}")
    private int maxKnown;

    @Value("${flybit.peer.min-known:1000}")
    private int minKnown;

    @Value("${flybit.peer.blacklisting-period:600000}")
    private long blacklistingPeriod;

    @Autowired
    private PeerService peerService;

    @Autowired
    private PeerClient peerClient;
    
    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    
    private final Set<Peer> peersKown = new HashSet<Peer>();;
    
    public PeerManagerImpl(){
        log.info("PeerDiscoverImpl instance created.");
    }
    

    @Override
    public void initSeeds() {

        if (offlineMode) {
            log.info("App is running in offline mode. Ignore initialize seeds.");
            return;
        }
        log.info("Begin initialize seeds.");

        if (resetPeersInDb) {
            peerService.deleteAll();
        }else{
            peerService.resetConnectedPeers();
        }

        final List<Peer> dbPeers = Lists.newArrayList(peerService.findAll());
        peersKown.addAll(dbPeers);
        final Set<Peer> peers = new HashSet<Peer>();

        defaultSeeds.forEach(address -> {
            if (!address.isEmpty()) {
                final Peer peer= PeerUtil.buildPeerFromAddress(address);
                peer.setPeerType(PeerType.DEFAULT);
                peers.add(peer);
            }
        });
        wellKnonwPeers.forEach(address -> {
            if (!address.isEmpty()) {
                final Peer peer= PeerUtil.buildPeerFromAddress(address);
                peer.setPeerType(PeerType.WELLKNOWN);
                peers.add(peer);
            }
        });

        final Set<Peer> peersToAddToDb = new HashSet<Peer>();
        peers.forEach(peer -> {
            if (!dbPeers.contains(peer)) {
                peersToAddToDb.add(peer);
            }
        });
        log.info("peersKnown "+peersKown);
        peersKown.addAll(peersToAddToDb);
        log.info("Begin initialize seeds.");
        peerService.saveAll(peersToAddToDb);
        log.info("peers added to DB "+peersToAddToDb);
    }

    @Override
    public void discoverPeers() {
        log.debug("Begin discover peers.");
        final long peersCount = peerService.count();
        if (peersCount> maxKnown) {
            return;
        }
        
        final Collection<Peer> workingPeers = peerConnectionManager.getWorkingPeers();
        log.debug("Working peers "+ workingPeers);
        if(workingPeers.isEmpty()){
            return;
        }
        
        workingPeers.forEach(peer-> log.debug("working peer "+ peer.getId()));
        final Peer selectedPeer=(Peer)workingPeers.toArray()[ThreadLocalRandom.current().nextInt(workingPeers.size())];
        log.debug("Selected peer "+ selectedPeer);
        
        final PeerInfoListMessage peerInfoListMessage = peerClient.getWorkingPeers(selectedPeer);
        
        final Set<Peer> peersToAddToDb = new HashSet<Peer>();
        log.debug("Got peers  "+ peerInfoListMessage.getPeerInfoMessages().size());
        for(final PeerInfoMessage peerInfoMessage: peerInfoListMessage.getPeerInfoMessages()){
            final Peer peer= PeerUtil.buildPeer(peerInfoMessage);
            if(!peerService.findByHostAndPort(peer.getHost(), peer.getPort()).isPresent()){
                peersToAddToDb.add(peer);
            }
            if(peersCount+peersToAddToDb.size()>maxKnown){
                break;
            }
        }
        log.debug("Peers need to add "+peersToAddToDb);
        if(!peersToAddToDb.isEmpty()){
            peerService.saveAll(peersToAddToDb);
        }
        
        
    }


    @Override
    public void unBlacklistingPeers() {
        final Iterable<Peer> peers = peerService.findBlacklistedPeers();
        peers.forEach(peer ->{
            if(peer.getLastBlacklistedInstant()+blacklistingPeriod<=System.currentTimeMillis()){
                peerService.unBlacklisting(peer.getId().toString());
                log.debug("UnBlacklisted peer "+ peer);
            }
        });
        
    }
}
