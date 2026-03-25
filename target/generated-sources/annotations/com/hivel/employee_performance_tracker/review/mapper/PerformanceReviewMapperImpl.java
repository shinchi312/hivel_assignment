package com.hivel.employee_performance_tracker.review.mapper;

import com.hivel.employee_performance_tracker.cycle.entity.ReviewCycle;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-26T01:31:58+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class PerformanceReviewMapperImpl implements PerformanceReviewMapper {

    @Override
    public PerformanceReviewResponse toResponse(PerformanceReview entity) {
        if ( entity == null ) {
            return null;
        }

        PerformanceReviewResponse.PerformanceReviewResponseBuilder performanceReviewResponse = PerformanceReviewResponse.builder();

        performanceReviewResponse.employeeId( entityEmployeeId( entity ) );
        performanceReviewResponse.employeeName( entityEmployeeName( entity ) );
        performanceReviewResponse.reviewCycleId( entityReviewCycleId( entity ) );
        performanceReviewResponse.reviewCycleName( entityReviewCycleName( entity ) );
        performanceReviewResponse.id( entity.getId() );
        performanceReviewResponse.rating( entity.getRating() );
        performanceReviewResponse.reviewerNotes( entity.getReviewerNotes() );
        performanceReviewResponse.submittedAt( entity.getSubmittedAt() );

        return performanceReviewResponse.build();
    }

    private Long entityEmployeeId(PerformanceReview performanceReview) {
        Employee employee = performanceReview.getEmployee();
        if ( employee == null ) {
            return null;
        }
        return employee.getId();
    }

    private String entityEmployeeName(PerformanceReview performanceReview) {
        Employee employee = performanceReview.getEmployee();
        if ( employee == null ) {
            return null;
        }
        return employee.getName();
    }

    private Long entityReviewCycleId(PerformanceReview performanceReview) {
        ReviewCycle reviewCycle = performanceReview.getReviewCycle();
        if ( reviewCycle == null ) {
            return null;
        }
        return reviewCycle.getId();
    }

    private String entityReviewCycleName(PerformanceReview performanceReview) {
        ReviewCycle reviewCycle = performanceReview.getReviewCycle();
        if ( reviewCycle == null ) {
            return null;
        }
        return reviewCycle.getName();
    }
}
