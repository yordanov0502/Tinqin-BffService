package com.tinqinacademy.bffservice.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.tinqinacademy.bffservice.core.operations..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String requestId = MDC.get("requestId");

        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String inputArgs = Arrays.toString(args);

        logger.info("==> Start requestId: {}. Method: {}(). Input: {}", requestId, methodName, inputArgs);
    }

    @AfterReturning(pointcut = "execution(* com.tinqinacademy.bffservice.core.operations..*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String requestId = MDC.get("requestId");
        String methodName = joinPoint.getSignature().getName();

        logger.info("<== End requestId: {}. Method: {}(). Output: {}", requestId, methodName, result);
    }
}
