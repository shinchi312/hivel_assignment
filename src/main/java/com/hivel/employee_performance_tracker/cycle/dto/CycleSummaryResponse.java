package com.hivel.employee_performance_tracker.cycle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CycleSummaryResponse {
    private Long cycleId;
    private String cycleName;
    private BigDecimal averageRating;
    private String topPerformerName;
    private long completedGoalsCount;
    private long missedGoalsCount;
}
