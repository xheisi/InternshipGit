package com.example.SpringJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by department.
    List<Employee> findByDepartment(String department);

    // Find employees whose salary is greater than a given amount.
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    // Find employees whose last name contains a given text.
    List<Employee> findByLastNameContaining(String text);

    // Find employees by department whose salary is greater than a given amount.
    List<Employee> findByDepartmentAndSalaryGreaterThan(String department, BigDecimal salary);

    //Using JPQL and the @Query annotation, create a repository method that returns all employees hired
    //after a given date.
    @Query("SELECT e FROM Employee e WHERE e.hireDate > :hireDate")
    List<Employee> findEmployeesHiredAfter(@Param("hireDate") LocalDate hireDate);

    //Using a native SQL query, create a repository method that returns all employees whose salary is
    //greater than a given amount.
    //@Query(value = "SELECT * FROM Employee e WHERE e.salary > 'amount'")

}
