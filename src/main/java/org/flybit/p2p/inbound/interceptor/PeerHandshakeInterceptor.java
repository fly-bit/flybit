package org.flybit.p2p.inbound.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.p2p.PeerConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.google.common.base.Strings;

public class PeerHandshakeInterceptor implements HandshakeInterceptor{
    private static final Log log = LogFactory.getLog(PeerHandshakeInterceptor.class);
    @Autowired
    private PeerConnectionManager peerConnectionManager;
    
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        log.debug("before handshake");
        if(peerConnectionManager.reachMaxInboundConnections()){
            log.info("reach Max Inbound Connections");
            return false;
        }
        if (request instanceof ServletServerHttpRequest) {
            final ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            final HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
            String ipAddress = httpServletRequest.getHeader("Remote_Addr");
            if(Strings.isNullOrEmpty(ipAddress)){
                ipAddress = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if(Strings.isNullOrEmpty(ipAddress)){
                ipAddress = httpServletRequest.getRemoteAddr();
            }
            
        }
        
        //TODO: If ip blaklisted, return false
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
        log.debug("After handshake");
    }

}
