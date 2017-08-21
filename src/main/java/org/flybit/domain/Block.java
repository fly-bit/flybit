package org.flybit.domain;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BLOCK")
public class Block extends BaseEntity<Block>{

    private static final long serialVersionUID = 4184622131350516773L;
    
    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    
    @Column(name = "PREVIOUS_BLOCK_ID", length = 64, updatable = false)
    private String previousBlockId;
    
    @Column(name = "TOTAL_FEE", nullable = false, scale = 16, updatable = false)
    private long totalFee;
    
    @Column(name = "PREVIOUS_BLOCK_HASH", updatable = false, columnDefinition="VARBINARY(32)")
    private byte[] previousBlockHash;
    
    @Column(name = "BLOCK_VERSION", nullable = false, scale = 16, updatable = false)
    private int blockVersion;
    
    @Column(name = "HEIGHT", nullable = false, updatable = false)
    private long height;

    @Column(name = "GENERATION_SIGNATURE", nullable = false,updatable = false, columnDefinition="VARBINARY(64)")
    private byte[] generationSignature;
    
    @Column(name = "BLOCK_SIGNATURE", nullable = false,updatable = false, columnDefinition="VARBINARY(64)")
    private byte[] blockSignature;
    
    @Column(name = "PAYLOAD_HASH", nullable = false,updatable = false, columnDefinition="VARBINARY(32)")
    private byte[] payloadHash;
    
    @Column(name = "GENERATOR_ID", nullable = false, length = 64, updatable = false)
    private String generatorId;
    
    @Column(name = "BASE_TARGET", nullable = false, updatable = false)
    private long baseTarget;
    
    @Column(name = "CUMULATIVE_DIFFICULTY", nullable = false, updatable = false)
    private BigInteger cumulativeDifficulty;

    @Column(name = "CREATION_INSTANT", nullable = false, scale = 10)
    private long creationInstant;
    
    @Column(name = "TRANSACTION_NUM", nullable = false, scale = 16, updatable = false)
    private int transactionNum;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreviousBlockId() {
        return previousBlockId;
    }

    public void setPreviousBlockId(String previousBlockId) {
        this.previousBlockId = previousBlockId;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public byte[] getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(byte[] previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public byte[] getGenerationSignature() {
        return generationSignature;
    }

    public void setGenerationSignature(byte[] generationSignature) {
        this.generationSignature = generationSignature;
    }

    public byte[] getBlockSignature() {
        return blockSignature;
    }

    public void setBlockSignature(byte[] blockSignature) {
        this.blockSignature = blockSignature;
    }

    public byte[] getPayloadHash() {
        return payloadHash;
    }

    public void setPayloadHash(byte[] payloadHash) {
        this.payloadHash = payloadHash;
    }

    public String getGeneratorId() {
        return generatorId;
    }

    public void setGeneratorId(String generatorId) {
        this.generatorId = generatorId;
    }

    public long getBaseTarget() {
        return baseTarget;
    }

    public void setBaseTarget(long baseTarget) {
        this.baseTarget = baseTarget;
    }

    public BigInteger getCumulativeDifficulty() {
        return cumulativeDifficulty;
    }

    public void setCumulativeDifficulty(BigInteger cumulativeDifficulty) {
        this.cumulativeDifficulty = cumulativeDifficulty;
    }

    public int getBlockVersion() {
        return blockVersion;
    }

    public void setBlockVersion(int blockVersion) {
        this.blockVersion = blockVersion;
    }

    public long getCreationInstant() {
        return creationInstant;
    }

    public void setCreationInstant(long creationInstant) {
        this.creationInstant = creationInstant;
    }

    public int getTransactionNum() {
        return transactionNum;
    }

    public void setTransactionNum(int transactionNum) {
        this.transactionNum = transactionNum;
    }

    
    //PAYLOAD_LENGTH?
    
    
}
