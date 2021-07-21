package com.example.payday.employee.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeNameTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("이름 바꾸기 트랜잭션")
    @Transactional
    void test_change_name() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;

        AddHourlyEmployee addHourlyEmployee =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        addHourlyEmployee.setEmployeeRepository(employeeRepository);
        addHourlyEmployee.execute();

        String changeName = "Bob";
        ChangeNameTransaction changeNameTransaction =
                new ChangeNameTransaction(empId, changeName);
        changeNameTransaction.setEmployeeRepository(employeeRepository);
        changeNameTransaction.execute();

        Employee employee = employeeRepository.findOne(empId);
        assertAll(
                () -> assertNotNull(employee),
                () -> assertEquals(changeName, employee.getName())
        );
    }


}