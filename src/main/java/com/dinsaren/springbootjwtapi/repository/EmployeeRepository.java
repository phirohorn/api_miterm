package com.dinsaren.springbootjwtapi.repository;

import com.dinsaren.springbootjwtapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("""
            SELECT e FROM Employee e
            WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(e.email) LIKE LOWER(CONCAT('%', :q, '%'))
            ORDER BY e.id DESC
            """)
    List<Employee> search(@Param("q") String query);

    List<Employee> findAllByOrderByIdDesc();

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmployeeCodeAndIdNot(String employeeCode, Integer id);
}
