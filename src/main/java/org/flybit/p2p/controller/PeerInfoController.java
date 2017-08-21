package org.flybit.p2p.controller;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.domain.Peer;
import org.flybit.p2p.PeerConnectionManager;
import org.flybit.p2p.message.PeerInfoListMessage;
import org.flybit.p2p.message.PeerInfoMessage;
import org.flybit.p2p.message.RequestMessage;
import org.flybit.service.PeerService;
import org.flybit.util.Constants;
import org.flybit.util.PeerUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class PeerInfoController {
    private static final Log log = LogFactory.getLog(PeerInfoController.class);

    @Autowired
    private PeerService peerService;
    
    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    @MessageMapping("/peerinfo")
    @SendToUser
    @Transactional
    public PeerInfoMessage getPeerInfo(PeerInfoMessage peerInfoMessage) {
        log.info("peerInfoMessage arrived with request id "+peerInfoMessage.getRequestId());
        if(peerInfoMessage.isShare()){
            final Peer peer = PeerUtil.buildPeer(peerInfoMessage);
            final Optional<Peer> peerInDbOptional = peerService.findByHostAndPort(peer.getHost(), peer.getPort());
            log.info("peerInfoMessage with host and port "+peer.getHost()+":"+peer.getPort()+" "+peerInDbOptional.isPresent());
            if(peerInDbOptional.isPresent()){
                final Peer peerInDb = peerService.findById(peerInDbOptional.get().getId()).get();
                BeanUtils.copyProperties(peer, peerInDb, "id","peerState");
                peerInDb.setMessage("saved in controller without save call");
                peerService.save(peerInDb);
            }else{
                peerService.save(peer);
            }
        }
        final PeerInfoMessage myPeerInfo = new PeerInfoMessage();
        BeanUtils.copyProperties(Constants.myPeerInfo, myPeerInfo);
        myPeerInfo.setRequestId(peerInfoMessage.getRequestId());
        return myPeerInfo;
    }

    @MessageMapping("/workingpeerinfo")
    @SendToUser
    public PeerInfoListMessage getWorkingPeerInfo(RequestMessage requestMessage) {
        log.info("peerInfoMessage arrived with request id "+requestMessage.getRequestId());
        final PeerInfoListMessage response = new PeerInfoListMessage();
        response.setRequestId(requestMessage.getRequestId());
        
        response.setPeerInfoMessages(PeerUtil.peerToPeerInfoMessage(peerConnectionManager.getWorkingPeers()));
        return response;
    }
    
}
