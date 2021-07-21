package com.example.payday.employee.classification.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.classification.SalariedClassification;
import com.example.payday.employee.command.AddCommissionedEmployee;
import com.example.payday.employee.schedule.MonthlySchedule;
import com.example.payday.employee.schedule.WeeklySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeSalariedTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("월급제로 바꾸기")
    @Transactional
    void test_change_hourly() {
        int empId = 3;
        String name = "Lance";
        String address = "Home";
        double salary = 2500;
        double commissionRate = 3.2;

        AddCommissionedEmployee addCommissionedEmployee =
                new AddCommissionedEmployee(empId, name, address, salary, commissionRate);
        addCommissionedEmployee.setEmployeeRepository(employeeRepository);
        addCommissionedEmployee.execute();

        double changeSalary = 1000.00;
        ChangeSalariedTransaction changeSalariedTransaction =
                new ChangeSalariedTransaction(empId, changeSalary);
        changeSalariedTransaction.setEmployeeRepository(employeeRepository);
        changeSalariedTransaction.execute();

        Employee employee = employeeRepository.findOne(empId);
        assertNotNull(employee);

        SalariedClassification sc = (SalariedClassification) employee.getClassification();
        assertAll(
                () -> assertNotNull(sc),
                () -> assertEquals(changeSalary, sc.getSalary())
        );

        MonthlySchedule ms = (MonthlySchedule) employee.getSchedule();
        assertNotNull(ms);
    }

}