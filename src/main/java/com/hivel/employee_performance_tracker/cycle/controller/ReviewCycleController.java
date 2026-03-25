package com.hivel.employee_performance_tracker.cycle.controller;

import com.hivel.employee_performance_tracker.cycle.dto.CreateCycleRequest;
import com.hivel.employee_performance_tracker.cycle.dto.CycleResponse;
import com.hivel.employee_performance_tracker.cycle.dto.CycleSummaryResponse;
import com.hivel.employee_performance_tracker.cycle.service.ReviewCycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cycles")
@Tag(name = "Review Cycles", description = "Review Cycle management endpoints")
@RequiredArgsConstructor
public class ReviewCycleController {

    private final ReviewCycleService cycleService;

    @GetMapping("/{id}/summary")
    @Operation(summary = "Get summary for a specific review cycle")
    public ResponseEntity<CycleSummaryResponse> getCycleSummary(@PathVariable Long id) {
        CycleSummaryResponse summary = cycleService.getCycleSummary(id);
        return ResponseEntity.ok(summary);
    }

    @PostMapping
    @Operation(summary = "Create a new review cycle")
    public ResponseEntity<CycleResponse> createCycle(@Valid @RequestBody CreateCycleRequest request) {
        CycleResponse response = cycleService.createCycle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
