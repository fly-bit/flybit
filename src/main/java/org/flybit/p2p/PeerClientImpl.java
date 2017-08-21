
package org.flybit.p2p;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.flybit.domain.PeerType;
import org.flybit.p2p.message.PeerInfoListMessage;
import org.flybit.p2p.message.PeerInfoMessage;
import org.flybit.p2p.message.RequestMessage;
import org.flybit.p2p.outbound.handler.PeerStompSessionHandler;
import org.flybit.service.PeerService;
import org.flybit.util.Constants;
import org.flybit.util.PlatformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
public class PeerClientImpl implements PeerClient {

    private static final Log log = LogFactory.getLog(PeerClientImpl.class);

    private static final String PEER_INFO_URL = "/app/peerinfo";
    private static final String CONNECTED_PEER_INFO_URL = "/app/workingpeerinfo";
    
    @Autowired
    private PeerService peerService;

    @Autowired
    private WebSocketStompClient webSocketStompClient;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    @Value("${flybit.my-address:}")
    private String myAddress;
    
    @Value("${flybit.share-address:true}")
    private boolean shareAddress;
    
    @PostConstruct
    public void init() {
        final PeerInfoMessage myPeerInfo = new PeerInfoMessage();
        myPeerInfo.setApplication(Constants.APPLICATION);
        myPeerInfo.setApplicationVersion(Constants.APPLICATION_VERSION);
        myPeerInfo.setPlatform(PlatformUtil.getPlatform());
        myPeerInfo.setAnnouncedAddress(myAddress);
        myPeerInfo.setShare(shareAddress);
        String host = null;
        int port = -1;
        if (!StringUtils.isEmpty(myAddress)) {
            URI uri;
            try {
                uri = new URI("http://" + myAddress);
                host = uri.getHost();
                port = (uri.getPort() == -1 ? Constants.DEFAULT_SERVER_PORT : uri.getPort());
            } catch (final URISyntaxException e) {
                throw new RuntimeException(e.toString(), e);
            }

        }

        myPeerInfo.setHost(host);
        myPeerInfo.setPort(port);
        myPeerInfo.setPeerType(PeerType.COMMON);
        
        Constants.myPeerInfo = myPeerInfo;
    }
    
    @Async
    @Override
    public void connect(Peer peer) {
        final String url = String.format("ws://%s:%s/ws", peer.getHost(), peer.getPort());
        final WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        handshakeHeaders.set("peer_host", peer.getHost());
        handshakeHeaders.set("peer_port", String.valueOf(peer.getPort()));

        final StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.set("peer_id", peer.getId().toString());

        final Peer peerInDb = peerService.findById(peer.getId()).get();
        peerInDb.setLastConnectInstant(System.currentTimeMillis());
        peerService.save(peerInDb);

        final PeerStompSessionHandler peerStompSessionHandler = new PeerStompSessionHandler(peer,
                applicationEventPublisher);

        webSocketStompClient.connect(url, handshakeHeaders, stompHeaders, peerStompSessionHandler);


    }

    @Override
    public PeerInfoMessage exchangePeerInfo(Peer peer) {
        
        return (PeerInfoMessage)peerConnectionManager.sendForResponse(peer, PEER_INFO_URL, Constants.myPeerInfo, 10, TimeUnit.MINUTES);
    }

    @Override
    public PeerInfoListMessage getWorkingPeers(Peer peer) {
        final RequestMessage requestMessage = new RequestMessage();
        return (PeerInfoListMessage)peerConnectionManager.sendForResponse(peer, CONNECTED_PEER_INFO_URL, requestMessage, 10, TimeUnit.MINUTES);
    }
    

}
