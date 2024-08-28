package com.tinqinacademy.bffservice.core.aspects;

import com.tinqinacademy.bffservice.api.base.OperationOutput;
import com.tinqinacademy.bffservice.api.exceptions.Errors;
import com.tinqinacademy.bffservice.core.exceptions.ExceptionService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ExceptionService exceptionService;

    @Around("execution(* com.tinqinacademy.bffservice.core.operations..*.*(..))")
    public Either<Errors, OperationOutput> logAround(ProceedingJoinPoint joinPoint) {

        String requestId = MDC.get("requestId");
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String inputArgs = Arrays.toString(args);

        logger.info("==> Start requestId: {}. Method: {}(). Input: {}", requestId, methodName, inputArgs);

        Either<Errors, OperationOutput> either = Try.of(() -> (OperationOutput) joinPoint.proceed())
                .toEither()
                .mapLeft(exceptionService::handle);

        if (either.isLeft()) {
            logger.error("<!!!> Exception in requestId: {}. Method: {}(). Error: {}", requestId, methodName, either.getLeft());
            return either;
        }

        logger.info("<== End requestId: {}. Method: {}(). Output: {}", requestId, methodName, either.get());
        return either;
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