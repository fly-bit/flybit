
package org.flybit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PUBLIC_KEY")
public class PublicKey extends BaseEntity<PublicKey> {

    private static final long serialVersionUID = -5555428653909508890L;

    @Id
    @Column(name = "ACCOUNT_ID", updatable = false, nullable = false)
    private String accountId;
    
    @Column(name = "ACCOUNT_TYPE", nullable = false, scale = 4, updatable = false)
    private byte accountType;

    @Column(name = "PUBLIC_KEY", nullable = false, updatable = false, columnDefinition = "VARBINARY(32)")
    private byte[] publicKey;

    @Column(name = "HEIGHT", nullable = false, updatable = false)
    private long height;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public byte getAccountType() {
        return accountType;
    }

    public void setAccountType(byte accountType) {
        this.accountType = accountType;
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
