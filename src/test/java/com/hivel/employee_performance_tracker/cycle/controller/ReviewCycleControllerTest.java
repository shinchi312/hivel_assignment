package com.hivel.employee_performance_tracker.cycle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivel.employee_performance_tracker.cycle.dto.CreateCycleRequest;
import com.hivel.employee_performance_tracker.cycle.dto.CycleResponse;
import com.hivel.employee_performance_tracker.cycle.dto.CycleSummaryResponse;
import com.hivel.employee_performance_tracker.cycle.service.ReviewCycleService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewCycleController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class ReviewCycleControllerTest {

        private final MockMvc mockMvc;
        private final ObjectMapper objectMapper;

        @MockitoBean
        private final ReviewCycleService cycleService;

        @Test
        void getCycleSummary_ShouldReturnOk() throws Exception {
                CycleSummaryResponse response = CycleSummaryResponse.builder()
                                .cycleId(1L)
                                .cycleName("Q1")
                                .build();

                when(cycleService.getCycleSummary(1L)).thenReturn(response);

                mockMvc.perform(get("/cycles/1/summary"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.cycleName").value("Q1"));
        }

        @Test
        void createCycle_ShouldReturnCreated() throws Exception {
                CreateCycleRequest request = CreateCycleRequest.builder()
                                .name("Q2")
                                .startDate(LocalDate.of(2025, 4, 1))
                                .endDate(LocalDate.of(2025, 6, 30))
                                .build();

                CycleResponse response = CycleResponse.builder()
                                .id(2L)
                                .name("Q2")
                                .startDate(request.getStartDate())
                                .endDate(request.getEndDate())
                                .build();

                when(cycleService.createCycle(any(CreateCycleRequest.class))).thenReturn(response);

                mockMvc.perform(post("/cycles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name").value("Q2"));
        }

        @Test
        void createCycle_ShouldReturnBadRequest_WhenNameIsMissing() throws Exception {
                CreateCycleRequest request = CreateCycleRequest.builder()
                                .startDate(LocalDate.of(2025, 4, 1))
                                .endDate(LocalDate.of(2025, 6, 30))
                                .build();

                mockMvc.perform(post("/cycles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest());
        }
}
