package com.hivel.employee_performance_tracker.review.service;

import com.hivel.employee_performance_tracker.cycle.entity.ReviewCycle;
import com.hivel.employee_performance_tracker.cycle.repository.ReviewCycleRepository;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import com.hivel.employee_performance_tracker.employee.repository.EmployeeRepository;
import com.hivel.employee_performance_tracker.review.dto.CreateReviewRequest;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import com.hivel.employee_performance_tracker.review.mapper.PerformanceReviewMapper;
import com.hivel.employee_performance_tracker.review.repository.PerformanceReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerformanceReviewServiceTest {

    @Mock
    private PerformanceReviewRepository reviewRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ReviewCycleRepository cycleRepository;
    @Mock
    private PerformanceReviewMapper reviewMapper;

    @InjectMocks
    private PerformanceReviewServiceImpl reviewService;

    @Test
    void submitReview_ShouldReturnResponse() {
        CreateReviewRequest request = CreateReviewRequest.builder()
                .employeeId(1L)
                .reviewCycleId(1L)
                .rating(5)
                .build();

        Employee employee = new Employee();
        ReviewCycle cycle = new ReviewCycle();
        PerformanceReview review = new PerformanceReview();
        PerformanceReviewResponse response = new PerformanceReviewResponse();
        response.setRating(5);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(cycleRepository.findById(1L)).thenReturn(Optional.of(cycle));
        when(reviewRepository.existsByEmployeeIdAndReviewCycleId(1L, 1L)).thenReturn(false);
        when(reviewRepository.save(any())).thenReturn(review);
        when(reviewMapper.toResponse(review)).thenReturn(response);

        PerformanceReviewResponse result = reviewService.submitReview(request);

        assertThat(result.getRating()).isEqualTo(5);
    }
}
