
package org.flybit.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.flybit.domain.Peer;
import org.flybit.p2p.message.PeerInfoMessage;
import org.springframework.beans.BeanUtils;

import com.google.common.net.HostAndPort;

public class PeerUtil {

    public static Peer buildPeerFromAddress(String address) {
        final Peer peer = new Peer();
        peer.setAnnouncedAddress(address);
        final HostAndPort hostAndPort = HostAndPort.fromString(address);
        String host = hostAndPort.getHostText();
        try {
            final InetAddress inetAddress = InetAddress.getByName(host);
            host = getHostAddress(inetAddress);
            peer.setHost(host);
            peer.setPort(hostAndPort.getPortOrDefault(Constants.DEFAULT_SERVER_PORT));
            
            return peer;
        } catch (final UnknownHostException e) {
            return null;
        }
    }

    
    public static Peer buildPeer(PeerInfoMessage peerInfoMessage) {
        final Peer peer = buildPeerFromAddress(peerInfoMessage.getAnnouncedAddress());
        peer.setApplication(peerInfoMessage.getApplication());
        peer.setApplicationVersion(peerInfoMessage.getApplicationVersion());
        peer.setPeerVersion(peerInfoMessage.getPeerVersion());
        peer.setPlatform(peerInfoMessage.getPlatform());
        peer.setPeerType(peerInfoMessage.getPeerType());
        peer.setShare(peerInfoMessage.isShare());
        return peer;
    }
    
    public static PeerInfoMessage peerToPeerInfoMessage(Peer peer){
        final PeerInfoMessage peerInfoMessage = new PeerInfoMessage();
        BeanUtils.copyProperties(peer, peerInfoMessage);
        return peerInfoMessage;
    }
    
    public static Collection<PeerInfoMessage> peerToPeerInfoMessage(Collection<Peer> peers){
        if(peers==null || peers.isEmpty()){
            return Collections.<PeerInfoMessage>emptyList();
        }
        final Set<PeerInfoMessage>  peerInfoMessages = new HashSet<>();
        for(final Peer peer: peers){
            peerInfoMessages.add(peerToPeerInfoMessage(peer));
        }
        
        return peerInfoMessages;
    }
    
    
    public static boolean isSameHostAndPort(Peer peer1, Peer peer2) {
        if (peer1 == null) {
            return false;
        }
        return peer1.getHost().equalsIgnoreCase(peer2.getHost()) && peer1.getPort() == peer2.getPort();
    }

    public static String getHostAddress(InetAddress inetAddress){
        String host = inetAddress.getHostAddress();
        //RFC2732
        if (host.split(":").length > 2) {
            host = "[" + host + "]";
        }
        return host;
    }
    
}
