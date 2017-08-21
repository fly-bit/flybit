package org.flybit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TRANSACTION")
public class Transaction extends BaseEntity<Transaction>{

    private static final long serialVersionUID = 4184622131350516773L;
    
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    
    @Column(name = "BLOCK_ID", nullable = false, length = 64, updatable = false)
    private String blockId;
    
    @Column(name = "SENDER_ID", nullable = false, length = 64, updatable = false)
    private String senderId;
    
    @Column(name = "RECIPIENT_ID", nullable = true, length = 64, updatable = false)
    private String recipientId;
    
    @Column(name = "EXPIRATION_INSTANT", nullable = false, scale = 10, updatable = false)
    private long expirationInstant;
    
    @Column(name = "AMOUNT", nullable = false, scale = 16, updatable = false)
    private long amount;
    
    @Column(name = "FEE", nullable = false, scale = 16, updatable = false)
    private int fee;
    
    @Column(name = "TRANSACTION_VERSION", nullable = false, scale = 16, updatable = false)
    private int transactionVersion;
    
    @Column(name = "HASH", nullable = false,updatable = false, columnDefinition="VARBINARY(32)")
    private byte[] hash;
    
    @Column(name = "SIGNATURE", nullable = false,updatable = false, columnDefinition="VARBINARY(64)")
    private byte[] signature;
    
    @Column(name = "BLOCK_HEIGHT", nullable = false, updatable = false)
    private long blockHeight;
    
    @Column(name = "TRANSACTION_TYPE", nullable = false, scale = 4, updatable = false)
    private byte transactionType;

    @Column(name = "TRANSACTION_INDEX", nullable = false, updatable = false)
    private int transactionIndex;
    
    @Column(name = "HAS_MESSAGE", nullable = false)
    private boolean hasMessage;
    
    @Column(name = "MESSAGE", nullable = true)
    private String message;
    
    @Column(name = "CREATION_INSTANT", nullable = false, scale = 10)
    private long creationInstant;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public long getExpirationInstant() {
        return expirationInstant;
    }

    public void setExpirationInstant(long expirationInstant) {
        this.expirationInstant = expirationInstant;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public byte getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(byte transactionType) {
        this.transactionType = transactionType;
    }

    public int getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(int transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public boolean isHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCreationInstant() {
        return creationInstant;
    }

    public void setCreationInstant(long creationInstant) {
        this.creationInstant = creationInstant;
    }

    public int getTransactionVersion() {
        return transactionVersion;
    }

    public void setTransactionVersion(int transactionVersion) {
        this.transactionVersion = transactionVersion;
    }
    
    
}
