package com.example.timetracking.service;

import com.example.timetracking.annotation.TrackTime;
import com.example.timetracking.dto.StatisticsDto;
import com.example.timetracking.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
@RequiredArgsConstructor
public class TimeStatisticsService {
    private final StatisticsRepository statisticsRepository;

    public List<StatisticsDto> getStatistics() {
        return statisticsRepository
                .findAll()
                .stream()
                .map(l -> new StatisticsDto(l.getMethod(),
                        l.getTime(),
                        l.getGroup(),
                        l.getLocalDateTime())).toList();
    }

    @TrackTime("service")
    public void test() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Test ok");
    }

    public void saveExecutionTime(String methodName, long executionTime) {

    }
}