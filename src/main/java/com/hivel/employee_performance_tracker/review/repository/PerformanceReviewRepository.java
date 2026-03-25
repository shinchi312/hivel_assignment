package com.hivel.employee_performance_tracker.review.repository;

import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    @EntityGraph(value = "PerformanceReview.withCycle", type = EntityGraph.EntityGraphType.FETCH)
    List<PerformanceReview> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndReviewCycleId(Long employeeId, Long reviewCycleId);
}
