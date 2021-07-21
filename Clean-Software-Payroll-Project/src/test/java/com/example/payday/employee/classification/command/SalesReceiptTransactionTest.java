package com.example.payday.employee.classification.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.CommissionedClassification;
import com.example.payday.employee.classification.SalesReceipt;
import com.example.payday.employee.command.AddCommissionedEmployee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesReceiptTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("판매 영수증 추가")
    @Transactional
    void add_sales_receipt() {
        int empId = 3;
        String name = "Lance";
        String address = "Home";
        double salary = 2500;
        double commissionRate = 3.2;

        AddCommissionedEmployee transaction =
                new AddCommissionedEmployee(empId, name, address, salary, commissionRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        SalesReceiptTransaction salesReceiptTransaction =
                new SalesReceiptTransaction(20211031, 100.00, 3);
        salesReceiptTransaction.setEmployeeRepository(employeeRepository);
        salesReceiptTransaction.execute();

        Employee employee = employeeRepository
                .findById(3)
                .orElse(null);
        assertNotNull(employee);

        CommissionedClassification cc =
                (CommissionedClassification) employee.getClassification();
        assertNotNull(cc);

        SalesReceipt salesReceipt = cc.getSalesReceipt(20211031);
        assertAll(
                () -> assertNotNull(salesReceipt),
                () -> assertEquals(100.00, salesReceipt.getAmount())
        );
    }

}