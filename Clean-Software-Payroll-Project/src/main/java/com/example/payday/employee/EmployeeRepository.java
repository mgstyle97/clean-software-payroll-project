package com.example.payday.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    default Employee findOne(int empId) {
        return this
                .findById(empId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Not Found employee: " + empId)
                );
    }
}
