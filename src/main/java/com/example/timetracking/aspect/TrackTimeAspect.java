package com.example.timetracking.aspect;

import com.example.timetracking.annotation.TrackTime;
import com.example.timetracking.entity.Statistic;
import com.example.timetracking.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
@RequiredArgsConstructor
public class TrackTimeAspect {
    private final StatisticsRepository repository;

    @Around("execution(@com.example.timetracking.annotation.TrackTime * *.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        TrackTime annotation = ((MethodSignature) joinPoint
                .getSignature())
                .getMethod()
                .getAnnotation(TrackTime.class);

        String group = (annotation.value().isEmpty()) ? "defaultGroup" : annotation.value();

        long start = System.currentTimeMillis();

        log.info("before ");

        Object proceed = joinPoint.proceed();

        log.info("after ");

        long executionTime = System.currentTimeMillis() - start;

        Statistic statistic = new Statistic();
        statistic.setTime(executionTime);
        statistic.setMethod(joinPoint.getSignature().toString());
        statistic.setGroup(group);

        repository.save(statistic);
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

//    @AfterReturning("trackTimeAnnotation()")
//    public void trackTime(JointPoint jointPoint){
//
//    }
}
