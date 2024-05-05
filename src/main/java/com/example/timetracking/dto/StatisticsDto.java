package com.example.timetracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;


public record StatisticsDto(
        String method,
        Long executionTime,
        String group,

        LocalDateTime localDateTime
) {
}
