package com.example.timetracking.services;

import com.example.timetracking.dto.StatisticSummaryDto;
import com.example.timetracking.dto.StatisticsDto;
import com.example.timetracking.dto.SummaryDto;
import com.example.timetracking.entity.Statistic;
import com.example.timetracking.repository.StatisticsRepository;
import com.example.timetracking.service.TimeStatisticsService;
import com.example.timetracking.service.maper.StaticsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TimeStatisticsServiceTest {

    private TimeStatisticsService timeStatisticsService;

    @Mock
    private StatisticsRepository statisticsRepository;

    @Mock
    private StaticsMapper mapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        timeStatisticsService = new TimeStatisticsService(statisticsRepository, mapper);
    }

    @Test
    public void testGetStatistics() {
        // Подготовка тестовых данных
        List<Statistic> statistics = new ArrayList<>();
        statistics.add(new Statistic(UUID.randomUUID(), "group1", "method1", LocalDateTime.now(), 100L));
        statistics.add(new Statistic(UUID.randomUUID(), "group1", "method1", LocalDateTime.now(), 200L));
        statistics.add(new Statistic(UUID.randomUUID(), "group2", "method2", LocalDateTime.now(), 150L));
        statistics.add(new Statistic(UUID.randomUUID(), "group2", "method2", LocalDateTime.now(), 250L));

        when(statisticsRepository.findAll()).thenReturn(statistics);

        // Мокирование метода toStaticDto()
        when(mapper.toStaticDto(statistics.get(0))).thenReturn(new StatisticsDto("method1", 100L, "group1", LocalDateTime.now()));
        when(mapper.toStaticDto(statistics.get(1))).thenReturn(new StatisticsDto("method1", 200L, "group1", LocalDateTime.now()));
        when(mapper.toStaticDto(statistics.get(2))).thenReturn(new StatisticsDto("method2", 150L, "group2", LocalDateTime.now()));
        when(mapper.toStaticDto(statistics.get(3))).thenReturn(new StatisticsDto("method2", 250L, "group2", LocalDateTime.now()));

        // Вызов тестируемого метода
        StatisticSummaryDto result = timeStatisticsService.getStatistics();

        // Проверка результатов
        assertEquals(2, result.getSummary().size()); // Проверяем, что возвращается корректное количество методов
        assertEquals(2, result.getSummary().get("method1").getAmount()); // Проверяем количество вызовов метода "method1"
        assertEquals(2, result.getSummary().get("method2").getAmount()); // Проверяем количество вызовов метода "method2"
        assertEquals(2, result.getTopSlow().size()); // Проверяем, что возвращается 2 самых медленных вызова
    }

    @Test
    public void testMapSummaryDto() {
        // Подготовка тестовых данных
        List<Statistic> statList = new ArrayList<>();
        statList.add(new Statistic(UUID.randomUUID(), "", "", LocalDateTime.now(), 100L));
        statList.add(new Statistic(UUID.randomUUID(), "", "", LocalDateTime.now(), 200L));
        statList.add(new Statistic(UUID.randomUUID(), "", "", LocalDateTime.now(), 300L));

        // Вызов тестируемого метода
        SummaryDto result = timeStatisticsService.mapSummaryDto(statList);

        // Проверка результатов
        assertEquals(3, result.getAmount()); // Проверяем количество вызовов
        assertEquals(300L, result.getMax()); // Проверяем максимальное время выполнения
        assertEquals(100L, result.getMin()); // Проверяем минимальное время выполнения
        assertEquals(200.0, result.getAvg()); // Проверяем среднее время выполнения
    }
}