package com.example.timetracking.dto;

import java.time.LocalDateTime;


public record StatisticsDto(
        String method,
        Long executionTime,
        String group,

        LocalDateTime localDateTime) {
}