package com.hivel.employee_performance_tracker.employee.controller;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeWithRatingResponse;
import com.hivel.employee_performance_tracker.employee.service.EmployeeService;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employees", description = "Employee management endpoints")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Create a new employee")
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody CreateEmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/reviews")
    @Operation(summary = "Get all reviews for an employee with cycle details")
    public ResponseEntity<List<PerformanceReviewResponse>> getEmployeeReviews(
            @PathVariable Long id) {
        List<PerformanceReviewResponse> reviews = employeeService.getReviewsByEmployeeId(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping
    @Operation(summary = "Filter employees by department and minimum average rating")
    public ResponseEntity<List<EmployeeWithRatingResponse>> getEmployeesByDepartmentAndRating(
            @RequestParam String department,
            @RequestParam Double minRating) {
        List<EmployeeWithRatingResponse> employees = employeeService.getEmployeesByDepartmentAndMinRating(department,
                minRating);
        return ResponseEntity.ok(employees);
    }
}
