package com.hivel.employee_performance_tracker.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeWithRatingResponse {

    private Long id;
    private String name;
    private String department;
    private String role;
    private LocalDate joiningDate;
    private BigDecimal averageRating;
}
