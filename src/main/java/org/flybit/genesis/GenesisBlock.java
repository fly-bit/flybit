
package org.flybit.genesis;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.flybit.crypto.Crypto;
import org.flybit.domain.Block;
import org.flybit.domain.Transaction;
import org.flybit.domain.TransactionType;
import org.flybit.transaction.TransactionBuilder;

public class GenesisBlock {
    public static final String BLOCK_ID = "fd750b89-6cb5-3dd6-8b58-5bf876482646";

    public static final byte[] BLOCK_SIGNATURE = new byte[] { -113, 28, 115, 21, -3, -54, -43, 95, -99, -57, 93, -115,
            43, -40, -38, -40, -33, -64, 99, 56, -33, 15, -94, -101, 56, 108, 115, 45, -128, 105, 30, 15, 41, -112, 28,
            -27, 105, -37, 38, 15, 88, 6, -82, 78, -58, -107, -106, 16, -23, 29, 99, -116, -36, -34, 41, 20, -23, -5,
            -119, -81, 50, -6, -111, -84 };

    public static final long BASE_TARGET = 153722867;
    private static List<Transaction> TRANSACTIONS;
    private static Block BLOCK;

    public static synchronized List<Transaction> getTransactions() {

        if (TRANSACTIONS == null) {
            TRANSACTIONS = new ArrayList<>();
            for (int i = 0; i < GenesisTransaction.RECIPIENTS.length; i++) {
                
                final TransactionBuilder builder = TransactionBuilder.newBuilder();
                builder.setAmount(GenesisTransaction.TRANSACTION_AMOUNTS[i]);
                builder.setFee(0);
                builder.setExpirationInstant(0);
                builder.setRecipientId(GenesisTransaction.RECIPIENTS[i]);
                builder.setSenderId(Genesis.GOD_ID);
                builder.setCreationInstant(Genesis.CREATION_INSTANT);
                builder.setTransactionType(TransactionType.PAYMENT.code());
                builder.setTransactionVersion(0);
                builder.setTransactionType(TransactionType.PAYMENT.code());
                builder.setHasMessage(false);
                builder.setSignature(GenesisTransaction.TRANSACTION_SIGNATURES[i]);
                builder.setTransactionIndex(i+1);
                builder.setHash(GenesisTransaction.TRANSACTION_HASHS[i]);
                builder.setId(GenesisTransaction.TRANSACTION_IDS[i]);
                builder.setBlockHeight(0);
                builder.setBlockId(BLOCK_ID);
                final Transaction transaction = builder.build();
                    
                TRANSACTIONS.add(transaction);
            }
        }
        

        return TRANSACTIONS;
    }
    
    public static synchronized Block getBlock() {
        
        if(BLOCK == null){
            final List<Transaction> transactions = getTransactions();
            final MessageDigest digest = Crypto.sha256();
            for(final Transaction transaction: transactions){
                digest.update(transaction.getHash());
            }
            final byte[] payloadHash = digest.digest();
            BLOCK = new Block();
            BLOCK.setId(BLOCK_ID);
            BLOCK.setHeight(0);
            BLOCK.setBaseTarget(BASE_TARGET);
            BLOCK.setBlockVersion(0);
            BLOCK.setCreationInstant(Genesis.CREATION_INSTANT);
            BLOCK.setPreviousBlockId(null);
            BLOCK.setPreviousBlockHash(null);
            BLOCK.setTotalFee(0);
            BLOCK.setGeneratorId(Genesis.GOD_ID);
            BLOCK.setTransactionNum(GenesisTransaction.RECIPIENTS.length);
            BLOCK.setPayloadHash(payloadHash);
            BLOCK.setGenerationSignature(new byte[64]);
            BLOCK.setCumulativeDifficulty(BigInteger.ZERO);
            BLOCK.setBlockSignature(BLOCK_SIGNATURE);
        }
        return BLOCK;
    }
    
}
