package com.hivel.employee_performance_tracker.employee.mapper;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.entity.Employee;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMapperTest {

    private final EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    void toEntity_ShouldMapCorrectly() {
        CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                .name("John")
                .department("IT")
                .role("Dev")
                .joiningDate(LocalDate.now())
                .build();

        Employee entity = mapper.toEntity(request);

        assertThat(entity.getName()).isEqualTo("John");
        assertThat(entity.getDepartment()).isEqualTo("IT");
    }

    @Test
    void toResponse_ShouldMapCorrectly() {
        Employee employee = Employee.builder()
                .id(1L)
                .name("John")
                .department("IT")
                .role("Dev")
                .joiningDate(LocalDate.now())
                .build();

        EmployeeResponse response = mapper.toResponse(employee);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("John");
    }
}
