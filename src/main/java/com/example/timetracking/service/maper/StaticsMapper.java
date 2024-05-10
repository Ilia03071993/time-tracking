package com.example.timetracking.service.maper;

import com.example.timetracking.dto.StatisticsDto;
import com.example.timetracking.entity.Statistic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StaticsMapper {
    StatisticsDto toStaticDto(Statistic statistic);
}
