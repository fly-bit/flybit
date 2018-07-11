package org.flybit.block;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.concensus.BlockDiscover;
import org.flybit.genesis.GenesisBlock;
import org.flybit.service.BlockService;
import org.flybit.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockManagerImpl implements BlockManager{

    private static final Log log = LogFactory.getLog(BlockManagerImpl.class);
    
    @Autowired
    private BlockService blockService;
    
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BlockDiscover blockDiscover;

    @Override
    public boolean initGenesisBlock() {
        if(blockService.existsById(GenesisBlock.BLOCK_ID)){
            log.info("Genesis block existed in DB.");
            return false;
        }else{
            log.info("Genesis block does not exist in DB.");
        }

        
        blockService.deleteAll();
        transactionService.deleteAll();
        
        transactionService.saveAll(GenesisBlock.getTransactions());
        blockService.save(GenesisBlock.getBlock());
        return true;
    }

    @Override
    public void discoverBlocks() {
        String myCurrentBlockId = getMyCurrentBlockId();
        Iterable<String> ids = blockDiscover.findBlockIds(myCurrentBlockId);

    }


    private String getMyCurrentBlockId(){
        return "";
    }

}
