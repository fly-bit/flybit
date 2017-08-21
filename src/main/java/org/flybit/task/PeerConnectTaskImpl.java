
package org.flybit.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.PeerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("!${flybit.offline-mode:false}")
public class PeerConnectTaskImpl implements PeerConnectTask {
    private static final Log log = LogFactory.getLog(PeerConnectTaskImpl.class);

    @Autowired
    private PeerConnector peerConnector;

    @Override
    @Scheduled(initialDelay = 10000, fixedDelay = 20000)
    public void connectPeers() {
        log.info("connect peers task");
        peerConnector.connectToSomePeers();
    }

}
