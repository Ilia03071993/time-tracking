package com.example.timetracking.repository;

import com.example.timetracking.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticsRepository extends JpaRepository<Statistic, Integer> {

}