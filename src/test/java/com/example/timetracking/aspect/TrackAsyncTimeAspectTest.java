package com.example.timetracking.aspect;

import com.example.timetracking.repository.StatisticsRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.concurrent.CompletableFuture;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TrackAsyncTimeAspectTest {

    @Mock
    private StatisticsRepository repository;

    @InjectMocks
    private TrackAsyncTimeAspect aspect;

    @Test
    public void testAsyncRunner() throws Throwable {
        // Arrange
        ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);

        // Act
        CompletableFuture<Void> result = (CompletableFuture<Void>) aspect.asyncRunner(joinPoint);

        // Assert
        result.get(); // Ждем завершения выполнения асинхронного метода
        verify(repository, times(1)).save(Mockito.any());
    }
}