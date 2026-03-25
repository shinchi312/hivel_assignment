package com.hivel.employee_performance_tracker.cycle.service;

import com.hivel.employee_performance_tracker.cycle.dto.CreateCycleRequest;
import com.hivel.employee_performance_tracker.cycle.dto.CycleResponse;
import com.hivel.employee_performance_tracker.cycle.dto.CycleSummaryResponse;
import com.hivel.employee_performance_tracker.cycle.entity.ReviewCycle;
import com.hivel.employee_performance_tracker.cycle.repository.ReviewCycleRepository;
import com.hivel.employee_performance_tracker.goal.repository.GoalRepository;
import com.hivel.employee_performance_tracker.review.repository.PerformanceReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewCycleServiceTest {

    @Mock
    private ReviewCycleRepository cycleRepository;
    @Mock
    private PerformanceReviewRepository reviewRepository;
    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private ReviewCycleServiceImpl cycleService;

    @Test
    void getCycleSummary_ShouldReturnSummary() {
        ReviewCycle cycle = ReviewCycle.builder().id(1L).name("Q1").build();

        when(cycleRepository.findById(1L)).thenReturn(Optional.of(cycle));
        when(reviewRepository.findAll()).thenReturn(Collections.emptyList());
        when(goalRepository.findByReviewCycleId(1L)).thenReturn(Collections.emptyList());

        CycleSummaryResponse result = cycleService.getCycleSummary(1L);

        assertThat(result.getCycleId()).isEqualTo(1L);
        assertThat(result.getCycleName()).isEqualTo("Q1");
    }

    @Test
    void createCycle_ShouldReturnSuccess() {
        CreateCycleRequest request = CreateCycleRequest.builder()
                .name("Q2")
                .startDate(LocalDate.of(2025, 4, 1))
                .endDate(LocalDate.of(2025, 6, 30))
                .build();

        ReviewCycle saved = ReviewCycle.builder()
                .id(2L)
                .name("Q2")
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        when(cycleRepository.save(any(ReviewCycle.class))).thenReturn(saved);

        CycleResponse result = cycleService.createCycle(request);

        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("Q2");
    }

    @Test
    void createCycle_ShouldThrowException_WhenEndDateIsBeforeStartDate() {
        CreateCycleRequest request = CreateCycleRequest.builder()
                .name("Invalid")
                .startDate(LocalDate.of(2025, 4, 1))
                .endDate(LocalDate.of(2025, 3, 31))
                .build();

        assertThrows(IllegalArgumentException.class, () -> cycleService.createCycle(request));
    }
}
