package com.hivel.employee_performance_tracker.employee.entity;

import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import com.hivel.employee_performance_tracker.goal.entity.Goal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String department;

    @Column(nullable = false, length = 100)
    private String role;

    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @OneToMany(mappedBy = "employee")
    @Builder.Default
    private List<PerformanceReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    @Builder.Default
    private List<Goal> goals = new ArrayList<>();
}
