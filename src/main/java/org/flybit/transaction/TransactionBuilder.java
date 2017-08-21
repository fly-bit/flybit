
package org.flybit.transaction;

import org.flybit.domain.Transaction;
import org.springframework.beans.BeanUtils;

public class TransactionBuilder {

    private String id;

    private String blockId;

    private String senderId;

    private String recipientId;

    private long expirationInstant;

    private long amount;

    private int fee;

    private int transactionVersion;

    private byte[] hash;

    private byte[] signature;

    private long blockHeight;

    private byte transactionType;

    private int transactionIndex;

    private boolean hasMessage;

    private String message;

    private long creationInstant;

    public static TransactionBuilder newBuilder() {
        return new TransactionBuilder();
    }

    //TODO: Use plain setters
    public Transaction build() {
        final Transaction transaction = new Transaction();
        BeanUtils.copyProperties(this, transaction);
        return transaction;
    }

    public TransactionBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public TransactionBuilder setTransactionVersion(int transactionVersion) {
        this.transactionVersion = transactionVersion;
        return this;
    }

    public TransactionBuilder setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
        return this;
    }

    public TransactionBuilder setBlockId(String blockId) {
        this.blockId = blockId;
        return this;
    }

    public TransactionBuilder setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public TransactionBuilder setRecipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public TransactionBuilder setExpirationInstant(long expirationInstant) {
        this.expirationInstant = expirationInstant;
        return this;
    }

    public TransactionBuilder setAmount(long amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder setFee(int fee) {
        this.fee = fee;
        return this;
    }

    public TransactionBuilder setHash(byte[] hash) {
        this.hash = hash;
        return this;
    }

    public TransactionBuilder setSignature(byte[] signature) {
        this.signature = signature;
        return this;
    }

    public TransactionBuilder setHeight(long blockHeight) {
        this.blockHeight = blockHeight;
        return this;
    }

    public TransactionBuilder setTransactionType(byte transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public TransactionBuilder setTransactionIndex(int transactionIndex) {
        this.transactionIndex = transactionIndex;
        return this;
    }

    public TransactionBuilder setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
        return this;
    }

    public TransactionBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public TransactionBuilder setCreationInstant(long creationInstant) {
        this.creationInstant = creationInstant;
        return this;
    }

    public String getBlockId() {
        return blockId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public long getExpiration() {
        return expirationInstant;
    }

    public long getAmount() {
        return amount;
    }

    public long getFee() {
        return fee;
    }

    public byte[] getHash() {
        return hash;
    }

    public byte[] getSignature() {
        return signature;
    }

    public long getHeight() {
        return blockHeight;
    }

    public byte getTransactionType() {
        return transactionType;
    }

    public int getTransactionIndex() {
        return transactionIndex;
    }

    public boolean isHasMessage() {
        return hasMessage;
    }

    public String getMessage() {
        return message;
    }

    public long getCreationInstant() {
        return creationInstant;
    }

    public String getId() {
        return id;
    }

    public long getExpirationInstant() {
        return expirationInstant;
    }

    public int getTransactionVersion() {
        return transactionVersion;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

}
