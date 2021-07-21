package com.example.payday.employee.method.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.command.AddHourlyEmployee;
import com.example.payday.employee.method.DirectMethod;
import com.example.payday.employee.method.MailMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeMailTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("급여 메일 지급으로 변경")
    @Transactional
    void test_change_mail() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;

        AddHourlyEmployee addHourlyEmployee =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        addHourlyEmployee.setEmployeeRepository(employeeRepository);
        addHourlyEmployee.execute();

        String mailAddress = "test@gmail.com";
        ChangeMailTransaction changeMailTransaction =
                new ChangeMailTransaction(empId, mailAddress);
        changeMailTransaction.setEmployeeRepository(employeeRepository);
        changeMailTransaction.execute();

        Employee employee = employeeRepository.findOne(empId);
        assertNotNull(employee);

        MailMethod mm = (MailMethod) employee.getMethod();
        assertAll(
                () -> assertNotNull(mm),
                () -> assertEquals(mailAddress, mm.getAddress())
        );
    }

}