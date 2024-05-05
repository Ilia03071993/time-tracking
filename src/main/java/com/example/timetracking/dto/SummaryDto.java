package com.example.timetracking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryDto {
    private Long min;
    private Long max;
    private Double avg;
    private Integer amount;
}
