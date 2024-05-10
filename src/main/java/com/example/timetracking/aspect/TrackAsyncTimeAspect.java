package com.example.timetracking.aspect;

import com.example.timetracking.annotation.TrackTime;
import com.example.timetracking.entity.Statistic;
import com.example.timetracking.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TrackAsyncTimeAspect {
    private final StatisticsRepository repository;

    @Around("execution(@com.example.timetracking.annotation.TrackAsyncTime * *.*(..))")
    public Object asyncRunner(ProceedingJoinPoint joinPoint) {
        return CompletableFuture.runAsync(() -> {
            try {
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
                statistic.setExecutionTime(executionTime);
                statistic.setMethodName(joinPoint.getSignature().toString());
                statistic.setGroupName(group);

                repository.save(statistic);
                System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
//                return proceed;
            } catch (Throwable e) {
                log.error("Ошибка AsyncRunnerAspect", e);
            }
        });
    }
}
