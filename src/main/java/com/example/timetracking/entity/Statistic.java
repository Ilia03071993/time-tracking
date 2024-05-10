package com.example.timetracking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    @Id
    @UuidGenerator
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "date_time")
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column(name = "execution_time_ms")
    private Long executionTime;
}