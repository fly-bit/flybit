
package org.flybit.p2p.message;

import org.flybit.domain.PeerType;

public class PeerInfoMessage extends RequestMessage{

    private String host;

    private int port;

    private String announcedAddress;

    private PeerType peerType;

    private String peerVersion;

    private String application;

    private String applicationVersion;

    private String platform;

    private boolean share;
    
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

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

}
