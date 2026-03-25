package com.hivel.employee_performance_tracker.review.controller;

import com.hivel.employee_performance_tracker.review.dto.CreateReviewRequest;
import com.hivel.employee_performance_tracker.review.dto.PerformanceReviewResponse;
import com.hivel.employee_performance_tracker.review.service.PerformanceReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class ReviewControllerTest {

        private final MockMvc mockMvc;
        private final ObjectMapper objectMapper;

        @MockitoBean
        private final PerformanceReviewService reviewService;

        @Test
        void submitReview_ShouldReturnCreated() throws Exception {
                CreateReviewRequest request = CreateReviewRequest.builder()
                                .employeeId(1L)
                                .reviewCycleId(1L)
                                .rating(5)
                                .build();

                PerformanceReviewResponse response = PerformanceReviewResponse.builder()
                                .id(1L)
                                .rating(5)
                                .build();

                when(reviewService.submitReview(any())).thenReturn(response);

                mockMvc.perform(post("/reviews")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.rating").value(5));
        }
}
