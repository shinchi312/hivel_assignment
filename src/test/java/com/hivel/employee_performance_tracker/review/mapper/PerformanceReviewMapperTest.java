package com.hivel.employee_performance_tracker.review.mapper;

import com.hivel.employee_performance_tracker.cycle.entity.ReviewCycle;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceReviewMapperTest {

    private final PerformanceReviewMapper mapper = Mappers.getMapper(PerformanceReviewMapper.class);

    @Test
    void toResponse_ShouldMapCorrectly() {
        Employee employee = Employee.builder().id(1L).name("John").build();
        ReviewCycle cycle = ReviewCycle.builder().id(10L).name("Q1").build();
        PerformanceReview review = PerformanceReview.builder()
                .id(100L)
                .employee(employee)
                .reviewCycle(cycle)
                .rating(5)
                .reviewerNotes("Good")
                .submittedAt(LocalDateTime.now())
                .build();

        PerformanceReviewResponse response = mapper.toResponse(review);

        assertThat(response.getId()).isEqualTo(100L);
        assertThat(response.getEmployeeId()).isEqualTo(1L);
        assertThat(response.getEmployeeName()).isEqualTo("John");
        assertThat(response.getReviewCycleId()).isEqualTo(10L);
        assertThat(response.getReviewCycleName()).isEqualTo("Q1");
    }
}
