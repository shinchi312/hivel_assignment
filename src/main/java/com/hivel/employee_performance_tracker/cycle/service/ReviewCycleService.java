package com.hivel.employee_performance_tracker.cycle.service;

import com.hivel.employee_performance_tracker.cycle.dto.CreateCycleRequest;
import com.hivel.employee_performance_tracker.cycle.dto.CycleResponse;
import com.hivel.employee_performance_tracker.cycle.dto.CycleSummaryResponse;

public interface ReviewCycleService {
    CycleSummaryResponse getCycleSummary(Long cycleId);

    CycleResponse createCycle(CreateCycleRequest request);
}
