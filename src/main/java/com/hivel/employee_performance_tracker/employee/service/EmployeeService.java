package com.hivel.employee_performance_tracker.employee.service;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeWithRatingResponse;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse createEmployee(CreateEmployeeRequest request);

    List<PerformanceReviewResponse> getReviewsByEmployeeId(Long employeeId);

    List<EmployeeWithRatingResponse> getEmployeesByDepartmentAndMinRating(String department, Double minRating);
}
