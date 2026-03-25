package com.hivel.employee_performance_tracker.employee.repository;

import com.hivel.employee_performance_tracker.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employees by department whose average performance rating across
     * all cycles is >= the given minimum rating.
     */
    @Query("""
                SELECT e FROM Employee e
                JOIN e.reviews r
                WHERE e.department = :department
                GROUP BY e
                HAVING AVG(r.rating) >= :minRating
            """)
    List<Employee> findByDepartmentAndMinAverageRating(
            @Param("department") String department,
            @Param("minRating") Double minRating);
}
