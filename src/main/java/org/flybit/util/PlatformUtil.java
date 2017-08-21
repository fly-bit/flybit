package org.flybit.util;

public class PlatformUtil {

    private PlatformUtil(){
    }
    

    public static String getPlatform(){
        final String platform = System.getProperty("os.name") + " " + System.getProperty("os.arch");
        return platform;
    }
    
    
    public static void main(String[] args){
        getPlatform();
    }
}
