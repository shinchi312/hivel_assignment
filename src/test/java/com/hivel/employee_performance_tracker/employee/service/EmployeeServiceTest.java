package com.hivel.employee_performance_tracker.employee.service;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import com.hivel.employee_performance_tracker.employee.mapper.EmployeeMapper;
import com.hivel.employee_performance_tracker.employee.repository.EmployeeRepository;
import com.hivel.employee_performance_tracker.review.repository.PerformanceReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private PerformanceReviewRepository reviewRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void createEmployee_ShouldReturnResponse() {
        CreateEmployeeRequest request = new CreateEmployeeRequest();
        Employee employee = new Employee();
        EmployeeResponse response = new EmployeeResponse();
        response.setName("John");

        when(employeeMapper.toEntity(request)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponse(employee)).thenReturn(response);

        EmployeeResponse result = employeeService.createEmployee(request);

        assertThat(result.getName()).isEqualTo("John");
    }
}
