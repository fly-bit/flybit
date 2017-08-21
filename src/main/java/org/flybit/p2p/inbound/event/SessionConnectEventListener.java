
package org.flybit.p2p.inbound.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.PeerConnectionManager;
import org.flybit.p2p.inbound.PeerInboundSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.session.web.socket.events.SessionConnectEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SessionConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    private static final Log log = LogFactory.getLog(SessionConnectEventListener.class);

    @Autowired
    private PeerInboundSessionRepository peerInboundSessionRepository;

    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    @Override
    public void onApplicationEvent(final SessionConnectEvent event) {
        final WebSocketSession webSocketSession = event.getWebSocketSession();
        final String ip = webSocketSession.getRemoteAddress().getHostString();
        peerConnectionManager.registerInboundSession(ip, webSocketSession);
       
        log.info(ip + " connected session id " + webSocketSession.getId());
    }

}
