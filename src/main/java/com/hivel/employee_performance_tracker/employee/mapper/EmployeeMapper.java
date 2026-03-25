package com.hivel.employee_performance_tracker.employee.mapper;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "goals", ignore = true)
    Employee toEntity(CreateEmployeeRequest request);

    EmployeeResponse toResponse(Employee employee);
}
