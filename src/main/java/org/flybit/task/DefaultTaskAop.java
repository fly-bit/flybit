package org.flybit.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.flybit.util.Constants;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DefaultTaskAop {
    private static final Log log = LogFactory.getLog(DefaultTaskAop.class);
    
    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void proceed(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if(!Constants.APPLICATION_READY){
            log.info("Application is not ready. Ignore the task "+ proceedingJoinPoint.getSignature());
            return;
        }
        proceedingJoinPoint.proceed();
    }
    
}
