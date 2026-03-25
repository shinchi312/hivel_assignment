package com.hivel.employee_performance_tracker.employee.repository;

import com.hivel.employee_performance_tracker.employee.entity.Employee;
import com.hivel.employee_performance_tracker.review.entity.PerformanceReview;
import com.hivel.employee_performance_tracker.cycle.entity.ReviewCycle;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestConstructor;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class EmployeeRepositoryTest {

        private final EmployeeRepository employeeRepository;
        private final TestEntityManager entityManager;

        @Test
        void findByDepartmentAndMinAverageRating_ShouldReturnMatchingEmployees() {
                // Given
                Employee emp1 = Employee.builder()
                                .name("John Doe")
                                .department("Engineering")
                                .role("Developer")
                                .joiningDate(LocalDate.now())
                                .build();
                entityManager.persist(emp1);

                ReviewCycle cycle = ReviewCycle.builder()
                                .name("Q1 2025")
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusMonths(3))
                                .build();
                entityManager.persist(cycle);

                PerformanceReview review1 = PerformanceReview.builder()
                                .employee(emp1)
                                .reviewCycle(cycle)
                                .rating(4)
                                .build();
                entityManager.persist(review1);

                Employee emp2 = Employee.builder()
                                .name("Jane Smith")
                                .department("Engineering")
                                .role("Lead")
                                .joiningDate(LocalDate.now())
                                .build();
                entityManager.persist(emp2);

                PerformanceReview review2 = PerformanceReview.builder()
                                .employee(emp2)
                                .reviewCycle(cycle)
                                .rating(2)
                                .build();
                entityManager.persist(review2);

                entityManager.flush();

                // When
                List<Employee> results = employeeRepository.findByDepartmentAndMinAverageRating("Engineering", 3.0);

                // Then
                assertThat(results).hasSize(1);
                assertThat(results.get(0).getName()).isEqualTo("John Doe");
        }
}
