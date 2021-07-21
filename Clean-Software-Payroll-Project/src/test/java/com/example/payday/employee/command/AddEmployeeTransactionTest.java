package com.example.payday.employee.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.CommissionedClassification;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.classification.SalariedClassification;
import com.example.payday.employee.method.HoldMethod;
import com.example.payday.employee.schedule.MonthlySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddEmployeeTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("월급 직원 추가")
    @Transactional
    void test_add_salaried_employee() {
        int empId = 1;
        String name = "Bob";
        String address = "Home";
        double salary = 1000.00;

        AddSalariedEmployee transaction =
                new AddSalariedEmployee(empId, name, address, salary);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        Employee employee = employeeRepository
                .findById(empId)
                .orElse(null);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(name, employee.getName())
        );

        SalariedClassification sc = (SalariedClassification) employee.getClassification();
        assertAll(
                () -> assertNotNull(sc),
                () -> assertEquals(salary, sc.getSalary())
        );

        MonthlySchedule ms = (MonthlySchedule) employee.getSchedule();
        assertNotNull(ms);

        HoldMethod hm = (HoldMethod) employee.getMethod();
        assertNotNull(hm);

    }

    @Test
    @DisplayName("시간제 직원 추가")
    @Transactional
    void test_add_hourly_employee() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;

        AddHourlyEmployee transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        Employee employee = employeeRepository
                .findById(empId)
                .orElse(null);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(name, employee.getName())
        );

        HourlyClassification hc = (HourlyClassification) employee.getClassification();
        assertAll(
                () -> assertNotNull(hc),
                () -> assertEquals(hourlyRate, hc.getHourlyRate())
        );
    }

    @Test
    @DisplayName("수수료를 받는 직원 추가")
    @Transactional
    void test_add_commissioned_employee() {
        int empId = 3;
        String name = "Lance";
        String address = "Home";
        double salary = 2500;
        double commissionRate = 3.2;
        AddCommissionedEmployee transaction =
                new AddCommissionedEmployee(empId, name, address, salary, commissionRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        Employee employee = employeeRepository
                .findById(empId)
                .orElse(null);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(name, employee.getName())
        );

        CommissionedClassification cc = (CommissionedClassification) employee.getClassification();
        assertAll(
                () -> assertNotNull(cc),
                () -> assertEquals(salary, cc.getSalary()),
                () -> assertEquals(commissionRate, cc.getCommissionRate())
        );
    }

}