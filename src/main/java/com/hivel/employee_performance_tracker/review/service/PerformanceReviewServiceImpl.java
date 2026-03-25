package com.hivel.employee_performance_tracker.review.service;

import com.hivel.employee_performance_tracker.cycle.entity.ReviewCycle;
import com.hivel.employee_performance_tracker.cycle.repository.ReviewCycleRepository;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import com.hivel.employee_performance_tracker.employee.repository.EmployeeRepository;
import com.hivel.employee_performance_tracker.exception.ResourceNotFoundException;
import com.hivel.employee_performance_tracker.review.dto.CreateReviewRequest;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import com.hivel.employee_performance_tracker.review.mapper.PerformanceReviewMapper;
import com.hivel.employee_performance_tracker.review.repository.PerformanceReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceReviewServiceImpl implements PerformanceReviewService {

    private final PerformanceReviewRepository reviewRepository;
    private final EmployeeRepository employeeRepository;
    private final ReviewCycleRepository cycleRepository;
    private final PerformanceReviewMapper reviewMapper;

    @Override
    @CacheEvict(value = { "cycleSummary", "employeeReviews",
            "employeeFilters" }, allEntries = true)
    public PerformanceReviewResponse submitReview(CreateReviewRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", request.getEmployeeId()));

        ReviewCycle cycle = cycleRepository.findById(request.getReviewCycleId())
                .orElseThrow(() -> new ResourceNotFoundException("Review Cycle", request.getReviewCycleId()));

        if (reviewRepository.existsByEmployeeIdAndReviewCycleId(request.getEmployeeId(), request.getReviewCycleId())) {
            throw new IllegalStateException("Employee already has a review for this cycle");
        }

        PerformanceReview review = PerformanceReview.builder()
                .employee(employee)
                .reviewCycle(cycle)
                .rating(request.getRating())
                .reviewerNotes(request.getReviewerNotes())
                .build();

        PerformanceReview saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }
}
