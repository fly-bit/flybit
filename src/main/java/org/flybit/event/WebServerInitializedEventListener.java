
package org.flybit.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flybit.util.Constants;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WebServerInitializedEventListener implements ApplicationListener<WebServerInitializedEvent> {

    private static final Log log = LogFactory.getLog(WebServerInitializedEventListener.class);

    @Override
    public void onApplicationEvent(final WebServerInitializedEvent event) {
        final int port = event.getWebServer().getPort();
        Constants.SERVER_PORT = port;
    }

}
