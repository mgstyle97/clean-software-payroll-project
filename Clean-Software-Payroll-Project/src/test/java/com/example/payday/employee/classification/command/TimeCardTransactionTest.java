package com.example.payday.employee.classification.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.classification.TimeCard;
import com.example.payday.employee.command.AddHourlyEmployee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TimeCardTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("타임카드 추가")
    @Transactional
    void test_add_timecard() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;

        AddHourlyEmployee transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        TimeCardTransaction timeCardTransaction =
                new TimeCardTransaction(20211031, 8.0, 2);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        Employee employee = employeeRepository
                .findById(2)
                .orElse(null);
        assertNotNull(employee);

        HourlyClassification hc = (HourlyClassification) employee.getClassification();
        assertNotNull(hc);

        TimeCard timeCard = hc.getTimeCard(20211031L);
        assertAll(
                () -> assertNotNull(timeCard),
                () -> assertEquals(8.0, timeCard.getHours())
        );

    }

}