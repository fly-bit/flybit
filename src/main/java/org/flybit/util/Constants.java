package org.flybit.util;

import org.flybit.p2p.message.PeerInfoMessage;

public final class Constants {

    public static final String APPLICATION_VERSION = "0.0.1";
    public static final String APPLICATION = "FRS";
    
    public static final int DEFAULT_SERVER_PORT = 7886;
    
    public static int SERVER_PORT = DEFAULT_SERVER_PORT;
    
    public static volatile boolean IS_TESTNET = false;
    
    public static volatile boolean APPLICATION_READY = false;
    
    public static PeerInfoMessage myPeerInfo;
    
    private Constants() {}
    
}
