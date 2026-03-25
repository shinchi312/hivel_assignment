package com.hivel.employee_performance_tracker.review.mapper;

import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PerformanceReviewMapper {

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee.name")
    @Mapping(target = "reviewCycleId", source = "reviewCycle.id")
    @Mapping(target = "reviewCycleName", source = "reviewCycle.name")
    PerformanceReviewResponse toResponse(PerformanceReview entity);
}
