
package org.flybit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseEntity<Account> {

    private static final long serialVersionUID = 4184622131350516773L;

    @Id
    @Column(updatable = false, nullable = false)
    private String id;
    
    @Column(name = "BALANCE", nullable = false, scale = 16, updatable = false)
    private long balance;

    @Column(name = "UNCONFIRMED_BALANCE", nullable = false, scale = 16, updatable = false)
    private long unconfirmedBalance;

    @Column(name = "PUBLIC_KEY", nullable = false, updatable = false, columnDefinition = "VARBINARY(32)")
    private byte[] publicKey;

    @Column(name = "HEIGHT", nullable = false, updatable = false)
    private long height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(long unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

}
