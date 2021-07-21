package com.example.payday.employee.classification.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.CommissionedClassification;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.command.AddHourlyEmployee;
import com.example.payday.employee.schedule.BiweeklySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeCommissionedTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("수수료받는 직원으로 변경")
    @Transactional
    void test_change_commissioned() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;

        AddHourlyEmployee transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        double salary = 2500;
        double commissionRate = 3.2;
        ChangeCommissionedTransaction changeCommissionedTransaction =
                new ChangeCommissionedTransaction(empId, salary, commissionRate);
        changeCommissionedTransaction.setEmployeeRepository(employeeRepository);
        changeCommissionedTransaction.execute();

        Employee employee = employeeRepository.findOne(empId);
        assertNotNull(employee);

        CommissionedClassification cc = (CommissionedClassification) employee.getClassification();
        assertAll(
                () -> assertNotNull(cc),
                () -> assertEquals(salary, cc.getSalary()),
                () -> assertEquals(commissionRate, cc.getCommissionRate())
        );

        BiweeklySchedule bs = (BiweeklySchedule) employee.getSchedule();
        assertNotNull(bs);
    }

}