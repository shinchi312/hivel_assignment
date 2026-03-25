package com.hivel.employee_performance_tracker.goal.repository;

import com.hivel.employee_performance_tracker.goal.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByReviewCycleId(Long reviewCycleId);
}
