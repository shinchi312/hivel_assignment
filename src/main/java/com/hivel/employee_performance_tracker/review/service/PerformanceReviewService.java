package com.hivel.employee_performance_tracker.review.service;

import com.hivel.employee_performance_tracker.review.dto.CreateReviewRequest;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;

public interface PerformanceReviewService {
    PerformanceReviewResponse submitReview(CreateReviewRequest request);
}
