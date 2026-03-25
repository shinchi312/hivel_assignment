package com.hivel.employee_performance_tracker.employee.controller;

import com.hivel.employee_performance_tracker.employee.dto.CreateEmployeeRequest;
import com.hivel.employee_performance_tracker.employee.dto.EmployeeResponse;
import com.hivel.employee_performance_tracker.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class EmployeeControllerTest {

        private final MockMvc mockMvc;
        private final ObjectMapper objectMapper;

        @MockitoBean
        private final EmployeeService employeeService;

        @Test
        void createEmployee_ShouldReturnCreated() throws Exception {
                CreateEmployeeRequest request = CreateEmployeeRequest.builder()
                                .name("John")
                                .department("IT")
                                .role("Dev")
                                .joiningDate(LocalDate.now())
                                .build();

                EmployeeResponse response = EmployeeResponse.builder()
                                .id(1L)
                                .name("John")
                                .build();

                when(employeeService.createEmployee(any())).thenReturn(response);

                mockMvc.perform(post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name").value("John"));
        }
}
