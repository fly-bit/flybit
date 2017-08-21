
package org.flybit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PEER")
public class Peer extends BaseEntity<Peer> {

    private static final long serialVersionUID = 4184622131350516773L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "HOST", nullable = false)
    private String host;

    @Column(name = "PORT", nullable = false)
    private int port;

    @Column(name = "ANNOUNCED_ADDRESS", length = 256, nullable = true)
    private String announcedAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "PEER_TYPE", length = 16, nullable = true)
    private PeerType peerType;

    @Column(name = "PEER_VERSION", length = 8, nullable = true)
    private String peerVersion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "PEER_STATE", length = 16, nullable = true)
    private PeerState peerState;
    
    @Column(name = "APPLICATION", length = 16, nullable = true)
    private String application;

    @Column(name = "APPLICATION_VERSION", length = 16, nullable = true)
    private String applicationVersion;

    @Column(name = "PLATFORM", length = 32, nullable = true)
    private String platform;

    @Column(name = "LAST_VALIDATE_INSTANT", nullable = true, scale = 10)
    private long lastValidateInstant;
    
    @Column(name = "LAST_CONNECTED_INSTANT", nullable = true, scale = 10)
    private long lastConnectedInstant;
    
    @Column(name = "LAST_CONNECT_INSTANT", nullable = true, scale = 10)
    private long lastConnectInstant;
    
    @Column(name = "LAST_DISCONNECTED_INSTANT", nullable = true, scale = 10)
    private long lastDisconnectedInstant;
    
    @Column(name = "LAST_BLACKLISTED_INSTANT", nullable = true, scale = 10)
    private long lastBlacklistedInstant;
    
    @Column(name = "IS_BLACKLISTED", nullable = false)
    private boolean isBlacklisted;
    
    @Column(name = "IS_SHARE", nullable = false)
    private boolean isShare;

    @Column(name = "MESSAGE", length = 128, nullable = true)
    private String message;
    
    public Peer() {
        this.lastValidateInstant = System.currentTimeMillis();
        this.peerState = PeerState.NON_CONNECTED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String getAnnouncedAddress() {
        return announcedAddress;
    }

    public void setAnnouncedAddress(String announcedAddress) {
        this.announcedAddress = announcedAddress;
    }

    public PeerType getPeerType() {
        return peerType;
    }

    public void setPeerType(PeerType peerType) {
        this.peerType = peerType;
    }

    public String getPeerVersion() {
        return peerVersion;
    }

    public void setPeerVersion(String peerVersion) {
        this.peerVersion = peerVersion;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getLastValidateInstant() {
        return lastValidateInstant;
    }

    public void setLastValidateInstant(long lastValidateInstant) {
        this.lastValidateInstant = lastValidateInstant;
    }

    public long getLastDisconnectedInstant() {
        return lastDisconnectedInstant;
    }

    public void setLastDisconnectedInstant(long lastDisconnectedInstant) {
        this.lastDisconnectedInstant = lastDisconnectedInstant;
    }

    public void setPeerState(PeerState peerState) {
        this.peerState = peerState;
    }

    public PeerState getPeerState() {
        return peerState;
    }

    public long getLastConnectedInstant() {
        return lastConnectedInstant;
    }

    public void setLastConnectedInstant(long lastConnectedInstant) {
        this.lastConnectedInstant = lastConnectedInstant;
    }

    public long getLastConnectInstant() {
        return lastConnectInstant;
    }

    public void setLastConnectInstant(long lastConnectInstant) {
        this.lastConnectInstant = lastConnectInstant;
    }

    public long getLastBlacklistedInstant() {
        return lastBlacklistedInstant;
    }

    public void setLastBlacklistedInstant(long lastBlacklistedInstant) {
        this.lastBlacklistedInstant = lastBlacklistedInstant;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(boolean isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean isShare) {
        this.isShare = isShare;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + port;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Peer other = (Peer) obj;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (port != other.port)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Peer [host=" + host + ", port=" + port + ", announcedAddress=" + announcedAddress + "]";
    }

}
