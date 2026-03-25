package com.hivel.employee_performance_tracker.review.controller;

import com.hivel.employee_performance_tracker.review.dto.CreateReviewRequest;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.service.PerformanceReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Reviews", description = "Performance Review management endpoints")
@RequiredArgsConstructor
public class ReviewController {

    private final PerformanceReviewService reviewService;

    @PostMapping
    @Operation(summary = "Submit a performance review")
    public ResponseEntity<PerformanceReviewResponse> submitReview(
            @Valid @RequestBody CreateReviewRequest request) {
        PerformanceReviewResponse response = reviewService.submitReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
