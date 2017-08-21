package org.flybit.block;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.flybit.crypto.Crypto;
import org.flybit.domain.Block;

public class BlockUtil {


    public static byte[] hashBeforeSign(Block block, byte[] generatorPublicKey) {
        final byte[] hash = Crypto.sha256().digest(getBytesBeforeSign(block, generatorPublicKey));
        return hash;
    }
    
    public static void sign(Block block, String secretPhrase){
        final byte[] generatorPublicKey = Crypto.getPublicKey(secretPhrase);
        final byte[] signature = Crypto.sign(hashBeforeSign(block, generatorPublicKey), secretPhrase);
        block.setBlockSignature(signature);
    }
    

    public static byte[] getBytesBeforeSign(Block block, byte[] generatorPublicKey) {
        final int bytesLength = 4+8+32+(block.getHeight()>0? 32:0)+64+32+8+4;
        final ByteBuffer buffer = ByteBuffer.allocate(bytesLength);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(block.getBlockVersion());// 4 bytes
        buffer.putLong(block.getCreationInstant()); //8 bytes
        buffer.put(block.getPayloadHash());//32 bytes
        if(block.getHeight()>0){
            buffer.put(block.getPreviousBlockHash());// 32 bytes
        }
        
        buffer.put(block.getGenerationSignature());// 64 bytes
        buffer.put(generatorPublicKey); //32 bytes
        buffer.putLong(block.getTotalFee()); //8 bytes
        buffer.putInt(block.getTransactionNum());  // 4 bytes
        
        return buffer.array();
    }
    
    
}
