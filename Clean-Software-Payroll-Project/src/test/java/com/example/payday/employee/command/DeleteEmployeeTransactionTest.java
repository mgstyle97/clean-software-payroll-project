package com.example.payday.employee.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteEmployeeTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("직원 삭제")
    void delete_employee_by_empId() {
        int empId = 3;
        String name = "Lance";
        String address = "Home";
        double salary = 2500;
        double commissionRate = 3.2;
        AddCommissionedEmployee transaction =
                new AddCommissionedEmployee(empId, name, address, salary, commissionRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        DeleteEmployeeTransaction deleteTransaction =
                new DeleteEmployeeTransaction(empId);
        deleteTransaction.setEmployeeRepository(employeeRepository);
        deleteTransaction.execute();

        Employee employee = employeeRepository
                .findById(empId)
                .orElse(null);
        assertNull(employee);

    }

}