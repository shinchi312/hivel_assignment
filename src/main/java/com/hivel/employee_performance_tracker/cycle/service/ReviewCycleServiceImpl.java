package com.hivel.employee_performance_tracker.cycle.service;

import com.hivel.employee_performance_tracker.cycle.dto.CreateCycleRequest;
import com.hivel.employee_performance_tracker.cycle.dto.CycleResponse;
import com.hivel.employee_performance_tracker.cycle.dto.CycleSummaryResponse;
import com.hivel.employee_performance_tracker.cycle.entity.ReviewCycle;
import com.hivel.employee_performance_tracker.cycle.repository.ReviewCycleRepository;
import com.hivel.employee_performance_tracker.exception.ResourceNotFoundException;
import com.hivel.employee_performance_tracker.goal.entity.Goal;
import com.hivel.employee_performance_tracker.goal.repository.GoalRepository;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import com.hivel.employee_performance_tracker.review.repository.PerformanceReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewCycleServiceImpl implements ReviewCycleService {

        private final ReviewCycleRepository cycleRepository;
        private final PerformanceReviewRepository reviewRepository;
        private final GoalRepository goalRepository;

        @Override
        @org.springframework.cache.annotation.Cacheable(value = "cycleSummary", key = "#cycleId")
        public CycleSummaryResponse getCycleSummary(Long cycleId) {
                ReviewCycle cycle = cycleRepository.findById(cycleId)
                                .orElseThrow(() -> new ResourceNotFoundException("Review Cycle", cycleId));

                List<PerformanceReview> reviews = reviewRepository.findAll().stream()
                                .filter(r -> r.getReviewCycle().getId().equals(cycleId))
                                .toList();

                // This is not efficient for 100k+ reviews, but for now it works.
                // In real world, we'd use aggregate SQL queries.

                double avgRating = reviews.stream()
                                .mapToInt(PerformanceReview::getRating)
                                .average()
                                .orElse(0.0);

                String topPerformer = reviews.stream()
                                .collect(Collectors.groupingBy(r -> r.getEmployee().getName(),
                                                Collectors.averagingDouble(PerformanceReview::getRating)))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElse("N/A");

                List<Goal> goals = goalRepository.findByReviewCycleId(cycleId);
                long completed = goals.stream().filter(g -> g.getStatus() == Goal.GoalStatus.COMPLETED).count();
                long missed = goals.stream().filter(g -> g.getStatus() == Goal.GoalStatus.MISSED).count();

                return CycleSummaryResponse.builder()
                                .cycleId(cycle.getId())
                                .cycleName(cycle.getName())
                                .averageRating(BigDecimal.valueOf(avgRating).setScale(2, RoundingMode.HALF_UP))
                                .topPerformerName(topPerformer)
                                .completedGoalsCount(completed)
                                .missedGoalsCount(missed)
                                .build();
        }

        @Override
        @Transactional
        public CycleResponse createCycle(CreateCycleRequest request) {
                if (request.getEndDate().isBefore(request.getStartDate())) {
                        throw new IllegalArgumentException("End date cannot be before start date");
                }

                ReviewCycle cycle = ReviewCycle.builder()
                                .name(request.getName())
                                .startDate(request.getStartDate())
                                .endDate(request.getEndDate())
                                .build();

                ReviewCycle saved = cycleRepository.save(cycle);

                return CycleResponse.builder()
                                .id(saved.getId())
                                .name(saved.getName())
                                .startDate(saved.getStartDate())
                                .endDate(saved.getEndDate())
                                .build();
        }
}
