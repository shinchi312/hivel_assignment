package com.hivel.employee_performance_tracker.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceReviewResponse {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long reviewCycleId;
    private String reviewCycleName;
    private Integer rating;
    private String reviewerNotes;
    private LocalDateTime submittedAt;
}
