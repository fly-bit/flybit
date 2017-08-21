
package org.flybit.event;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.block.BlockManager;
import org.flybit.p2p.PeerManager;
import org.flybit.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Log log = LogFactory.getLog(ApplicationReadyEventListener.class);

    @Autowired
    private Environment environment;

    @Autowired
    private PeerManager peerManager;
    
    @Autowired
    private BlockManager blockManager;
    
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        final String[] profiles = environment.getActiveProfiles();
        log.info("Application is running as ." + profiles.length);
        if (Arrays.stream(profiles).anyMatch(env -> (env.equalsIgnoreCase("testnet")))) {
            Constants.IS_TESTNET = true;
            log.info("Application is running as testnet.");
        }

        peerManager.initSeeds();
        blockManager.initGenesisBlock();
        
        Constants.APPLICATION_READY = true;
        
        log.info("Application started.");
    }

}
