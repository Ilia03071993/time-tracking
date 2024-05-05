package com.example.timetracking.controller;

import com.example.timetracking.annotation.TrackTime;
import com.example.timetracking.dto.StatisticSummaryDto;
import com.example.timetracking.dto.StatisticsDto;
import com.example.timetracking.service.TimeStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class TimeStatisticsController {
    private final TimeStatisticsService timeStatisticsService;

    @TrackTime("controller")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticSummaryDto getStatistics() {
        return timeStatisticsService.getStatistics();
    }

    @GetMapping("/test")
    public void test() {
        timeStatisticsService.test();
    }
}