package com.hivel.employee_performance_tracker.cycle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CycleResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
