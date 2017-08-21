
package org.flybit.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.PeerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("!${flybit.offline-mode:false}")
public class PeerUnBlacklistingTaskImpl implements PeerUnBlacklistingTask {
    private static final Log log = LogFactory.getLog(PeerUnBlacklistingTaskImpl.class);

    @Autowired
    private PeerManager peerManager;

    @Override
    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void unBlacklistingPeers() {
        log.info("unBlacklisting peers task");
        peerManager.unBlacklistingPeers();
    }

}
