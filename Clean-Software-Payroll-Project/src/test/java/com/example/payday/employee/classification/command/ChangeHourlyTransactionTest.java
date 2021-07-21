package com.example.payday.employee.classification.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.command.AddCommissionedEmployee;
import com.example.payday.employee.command.AddHourlyEmployee;
import com.example.payday.employee.command.ChangeNameTransaction;
import com.example.payday.employee.schedule.WeeklySchedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeHourlyTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("시간제로 바꾸기")
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

        double hourlyRate = 27.52;
        ChangeHourlyTransaction changeHourlyTransaction =
                new ChangeHourlyTransaction(empId, hourlyRate);
        changeHourlyTransaction.setEmployeeRepository(employeeRepository);
        changeHourlyTransaction.execute();

        Employee employee = employeeRepository.findOne(empId);
        assertNotNull(employee);

        HourlyClassification hc = (HourlyClassification) employee.getClassification();
        assertAll(
                () -> assertNotNull(hc),
                () -> assertEquals(hourlyRate, hc.getHourlyRate())
        );

        WeeklySchedule ws = (WeeklySchedule) employee.getSchedule();
        assertNotNull(ws);
    }

}