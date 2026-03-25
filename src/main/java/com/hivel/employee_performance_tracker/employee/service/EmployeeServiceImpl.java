package com.hivel.employee_performance_tracker.employee.service;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeWithRatingResponse;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import com.hivel.employee_performance_tracker.employee.mapper.EmployeeMapper;
import com.hivel.employee_performance_tracker.employee.repository.EmployeeRepository;
import com.hivel.employee_performance_tracker.exception.ResourceNotFoundException;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import com.hivel.employee_performance_tracker.review.mapper.PerformanceReviewMapper;
import com.hivel.employee_performance_tracker.review.repository.PerformanceReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository reviewRepository;
    private final EmployeeMapper employeeMapper;
    private final PerformanceReviewMapper reviewMapper;

    @Override
    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        Employee employee = employeeMapper.toEntity(request);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(value = "employeeReviews", key = "#employeeId")
    public List<PerformanceReviewResponse> getReviewsByEmployeeId(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee", employeeId);
        }

        List<PerformanceReview> reviews = reviewRepository.findByEmployeeId(employeeId);
        return reviews.stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(value = "employeeFilters", key = "#department + '-' + #minRating")
    public List<EmployeeWithRatingResponse> getEmployeesByDepartmentAndMinRating(
            String department, Double minRating) {
        List<Employee> employees = employeeRepository
                .findByDepartmentAndMinAverageRating(department, minRating);

        return employees.stream()
                .map(this::toEmployeeWithRating)
                .toList();
    }

    private EmployeeWithRatingResponse toEmployeeWithRating(Employee employee) {
        double avg = employee.getReviews().stream()
                .mapToInt(PerformanceReview::getRating)
                .average()
                .orElse(0.0);

        return EmployeeWithRatingResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .department(employee.getDepartment())
                .role(employee.getRole())
                .joiningDate(employee.getJoiningDate())
                .averageRating(BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP))
                .build();
    }
}
