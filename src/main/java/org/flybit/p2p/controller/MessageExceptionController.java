package org.flybit.p2p.controller;

import java.util.logging.Logger;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class MessageExceptionController {
    private static final Logger log = Logger.getLogger(MessageExceptionController.class.getName());


    @MessageExceptionHandler
    @SendToUser(value = "/queue/errors", broadcast = false)
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
    
}
