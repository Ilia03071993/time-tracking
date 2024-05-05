package com.example.timetracking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class StatisticSummaryDto {
    private Map<String, SummaryDto> summary;
    private List<StatisticsDto> topSlow;
}
