package com.hivel.employee_performance_tracker.employee.mapper;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-26T01:31:59+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEntity(CreateEmployeeRequest request) {
        if ( request == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.name( request.getName() );
        employee.department( request.getDepartment() );
        employee.role( request.getRole() );
        employee.joiningDate( request.getJoiningDate() );

        return employee.build();
    }

    @Override
    public EmployeeResponse toResponse(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeResponse.EmployeeResponseBuilder employeeResponse = EmployeeResponse.builder();

        employeeResponse.id( employee.getId() );
        employeeResponse.name( employee.getName() );
        employeeResponse.department( employee.getDepartment() );
        employeeResponse.role( employee.getRole() );
        employeeResponse.joiningDate( employee.getJoiningDate() );

        return employeeResponse.build();
    }
}
