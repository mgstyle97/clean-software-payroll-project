package com.example.payday.employee.affiliation.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.affiliation.ServiceCharge;
import com.example.payday.employee.affiliation.UnionAffiliation;
import com.example.payday.employee.command.AddHourlyEmployee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceChargeTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("조합 공제액 추가")
    @Transactional
    void add_service_charge() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;

        AddHourlyEmployee transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        ChangeAffiliatedTransaction affiliatedTransaction =
                new ChangeAffiliatedTransaction(empId, 12.5);
        affiliatedTransaction.setEmployeeRepository(employeeRepository);
        affiliatedTransaction.execute();

        ServiceChargeTransaction serviceChargeTransaction =
                new ServiceChargeTransaction(2, 20211031, 12.95);
        serviceChargeTransaction.setEmployeeRepository(employeeRepository);
        serviceChargeTransaction.execute();

        Employee employee = employeeRepository.findOne(empId);
        assertNotNull(employee);

        UnionAffiliation uaf = (UnionAffiliation) employee.getAffiliation();
        assertNotNull(uaf);

        ServiceCharge sc = uaf.getServiceCharge(20211031);
        assertAll(
                () -> assertNotNull(sc),
                () -> assertEquals(12.95, sc.getCharge())
        );
    }

}