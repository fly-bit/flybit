package org.flybit.p2p.inbound.event;

import org.flybit.p2p.inbound.PeerInboundSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class PresenceEventListener {
	
	
	private final SimpMessagingTemplate messagingTemplate;
	
    @Autowired
    private PeerInboundSessionRepository peerInboundSessionRepository;
    
	private String loginDestination;
	
	private String logoutDestination;
	
	public PresenceEventListener(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}
		
	@EventListener
	private void handleSessionConnected(SessionConnectEvent event) {
		final SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//		final String username = headers.getUser().getName();
//
//        final String sessionId = headers.getSessionId();
//        peerWebSocketSessionRepository.put(username, sessionId);
        
//		final LoginEvent loginEvent = new LoginEvent(username);
//		messagingTemplate.convertAndSend(loginDestination, loginEvent);
//		messagingTemplate.convertAndSend("/topic/trans", "test queue tran message");
        
		
		// We store the session as we need to be idempotent in the disconnect event processing
//		participantRepository.add(headers.getSessionId(), loginEvent);
	}
	
	@EventListener
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
	    peerInboundSessionRepository.remove(event.getSessionId());
	    
	}

	public void setLoginDestination(String loginDestination) {
		this.loginDestination = loginDestination;
	}

	public void setLogoutDestination(String logoutDestination) {
		this.logoutDestination = logoutDestination;
	}
}
