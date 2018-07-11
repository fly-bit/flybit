package org.flybit.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.block.BlockManager;
import org.flybit.concensus.BlockDiscover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${flybit.peer.discover-new-peer:true} and !${flybit.offline-mode:false}")
public class BlockDiscoverTaskImpl implements BlockDiscoverTask{
    private static final Log log = LogFactory.getLog(BlockDiscoverTaskImpl.class);

    @Autowired
    private BlockManager blockManager;

    private volatile boolean isGenesisBlockAdded = false;

    @Override
    @Scheduled(initialDelay=3000, fixedDelay = 10000)
    public void discoverBlocks() {
        log.info("discover block");
        if (!isGenesisBlockAdded) {
            addGenesisBlock();
        }else{
            log.info("Genesis check pass.");
        }
        blockManager.discoverBlocks();
    }

    private void addGenesisBlock() {
        blockManager.initGenesisBlock();
        isGenesisBlockAdded=true;
    }
    
}
