package com.example.timetracking.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrackAsyncTimeAspect {
    @Pointcut("@annotation(com.example.timetracking.annotation.TrackAsyncTime)")
    public void trackAsyncTimeAnnotation() {}

//    @AfterReturning("trackAsyncTimeAnnotation()")
//    @Async
//    public void trackAsyncTime(JoinPoint joinPoint) {
//
//    }
}
