package com.example.timetracking.aspect;

import com.example.timetracking.annotation.TrackTime;
import com.example.timetracking.entity.Statistic;
import com.example.timetracking.repository.StatisticsRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

public class TrackTimeAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature signature;

    @Mock
    private TrackTime annotation;

    @Mock
    private StatisticsRepository repository;

    @InjectMocks
    private TrackTimeAspect aspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLogExecutionTime() throws Throwable {
        // Arrange
        Method method = MyClass.class.getMethod("myMethod"); // MyClass и myMethod должны быть заменены на ваш класс и метод
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getMethod()).thenReturn(method);
        when(signature.toString()).thenReturn("someMethodSignature");
        when(annotation.value()).thenReturn("groupName");

        // Act
        aspect.logExecutionTime(joinPoint);

        // Assert
        verify(repository, times(1)).save(any(Statistic.class));
    }

    // Пример класса для демонстрации
    static class MyClass {
        public void myMethod() {
            System.out.println("Hello");
        }
    }
}