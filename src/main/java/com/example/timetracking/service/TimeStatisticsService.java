package com.example.timetracking.service;

import com.example.timetracking.annotation.TrackTime;
import com.example.timetracking.dto.StatisticSummaryDto;
import com.example.timetracking.dto.SummaryDto;
import com.example.timetracking.entity.Statistic;
import com.example.timetracking.repository.StatisticsRepository;
import com.example.timetracking.service.maper.StaticsMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TimeStatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final StaticsMapper mapper;

    public StatisticSummaryDto getStatistics() {
//        return statisticsRepository
//                .findAll()
//                .stream()
//                .map(l -> new StatisticsDto(l
//                        .getMethod(),
//                        l.getTime(),
//                        l.getGroup(),
//                        l.getLocalDateTime())).toList();

        StatisticSummaryDto statisticSummaryDto = new StatisticSummaryDto();

        Map<String, SummaryDto> summaryDtoMap = new HashMap<>();

        List<Statistic> statistics = statisticsRepository.findAll();

        Map<String, List<Statistic>> collect = statistics.stream()
                .collect(Collectors.groupingBy(statistic -> statistic.getMethodName()));

        collect.forEach((methodName, statList) -> summaryDtoMap.put(methodName, mapSummaryDto(statList)));

        statisticSummaryDto.setSummary(summaryDtoMap);
        statisticSummaryDto.setTopSlow(statistics.stream()
                .sorted(Comparator.comparing(Statistic::getExecutionTime).reversed())
                .map(mapper::toStaticDto)
                .limit(10)
                .toList()
        );

        return statisticSummaryDto;
    }

    private SummaryDto mapSummaryDto(List<Statistic> statList) {
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setAmount(statList.size());
        summaryDto.setMax(statList.stream().map(t -> t.getExecutionTime()).max(Long::compare).get());
        summaryDto.setMin(statList.stream().map(t -> t.getExecutionTime()).min(Long::compare).get());
        summaryDto.setAvg((double) (statList.stream()
                .map(t -> t.getExecutionTime())
                .mapToLong(v -> v).sum() / statList.size()));

        return summaryDto;
    }

    @SneakyThrows
    @TrackTime
    public void test() {
        log.info("start");

        TimeUnit.SECONDS.sleep(2);

        Thread thread = new Thread(this::testThread);
        thread.start();

        log.info("Test ok");
    }

    @SneakyThrows
    private void testThread() {
        log.info("start");

        TimeUnit.SECONDS.sleep(2);

        log.info("end");
    }

    public void saveExecutionTime(String methodName, long executionTime) {

    }
}