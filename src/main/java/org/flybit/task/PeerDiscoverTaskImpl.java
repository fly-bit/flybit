package org.flybit.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.PeerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${flybit.peer.discover-new-peer:true} and !${flybit.offline-mode:false}")
public class PeerDiscoverTaskImpl implements PeerDiscoverTask{
    private static final Log log = LogFactory.getLog(PeerDiscoverTaskImpl.class);

    @Autowired
    private PeerManager peerManager;
    
    private volatile boolean seedsInitialized = false;

    @Override
    @Scheduled(initialDelay=3000, fixedDelay = 10000)
    public void discoverPeers() {
        if (!seedsInitialized) {
            initializeSeeds();
        }
        peerManager.discoverPeers();
    }

    private void initializeSeeds() {
        peerManager.initSeeds();
        seedsInitialized=true;
    }
    
}
