package com.example.timetracking.repository;

import com.example.timetracking.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatisticsRepository extends JpaRepository<Statistic, UUID> {

}