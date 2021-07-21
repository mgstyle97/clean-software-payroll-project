package com.example.payday.employee.method.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.command.AddHourlyEmployee;
import com.example.payday.employee.method.DirectMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChangeDirectTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("급여 직접 지급으로 변경")
    @Transactional
    void test_change_direct() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;

        AddHourlyEmployee addHourlyEmployee =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        addHourlyEmployee.setEmployeeRepository(employeeRepository);
        addHourlyEmployee.execute();

        String bank = "IBK";
        String account = "11-1111-1111-11";
        ChangeDirectTransaction changeDirectTransaction =
                new ChangeDirectTransaction(empId, bank, account);
        changeDirectTransaction.setEmployeeRepository(employeeRepository);
        changeDirectTransaction.execute();

        Employee employee = employeeRepository.findOne(empId);
        assertNotNull(employee);

        DirectMethod dm = (DirectMethod) employee.getMethod();
        assertAll(
                () -> assertNotNull(dm),
                () -> assertEquals(bank, dm.getBank()),
                () -> assertEquals(account, dm.getAccount())
        );
    }

}