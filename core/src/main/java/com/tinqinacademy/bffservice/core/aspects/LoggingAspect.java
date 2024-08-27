package com.tinqinacademy.bffservice.core.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.tinqinacademy.bffservice.core.operations..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        String requestId = MDC.get("requestId");
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String inputArgs = Arrays.toString(args);

        logger.info("==> Start requestId: {}. Method: {}(). Input: {}", requestId, methodName, inputArgs);

        Object result;
        result = joinPoint.proceed();

        logger.info("<== End requestId: {}. Method: {}(). Output: {}", requestId, methodName, result);

        return result;
    }

//    @Before("execution(* com.tinqinacademy.bffservice.core.operations..*.*(..))")
//    public void logBefore(JoinPoint joinPoint) {
//        String requestId = MDC.get("requestId");
//
//        Object[] args = joinPoint.getArgs();
//        String methodName = joinPoint.getSignature().getName();
//        String inputArgs = Arrays.toString(args);
//
//        logger.info("==> Start requestId: {}. Method: {}(). Input: {}", requestId, methodName, inputArgs);
//    }
//
//    @AfterReturning(pointcut = "execution(* com.tinqinacademy.bffservice.core.operations..*.*(..))", returning = "result")
//    public void logAfterReturning(JoinPoint joinPoint, Object result) {
//        String requestId = MDC.get("requestId");
//        String methodName = joinPoint.getSignature().getName();
//
//        logger.info("<== End requestId: {}. Method: {}(). Output: {}", requestId, methodName, result);
//    }
}