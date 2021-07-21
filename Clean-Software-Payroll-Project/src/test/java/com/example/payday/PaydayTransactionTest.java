package com.example.payday;

import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.command.TimeCardTransaction;
import com.example.payday.employee.command.AddEmployeeTransaction;
import com.example.payday.employee.command.AddHourlyEmployee;
import com.example.payday.utils.DateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaydayTransactionTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("시간제 직원에게 임금 지급 - 타임카드 없이")
    @Transactional
    void test_pay_single_hourly_employee_no_timecard() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;
        AddEmployeeTransaction transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        long payDate = 20011109;
        PaydayTransaction paydayTransaction =
                new PaydayTransaction(payDate);
        paydayTransaction.setEmployeeRepository(employeeRepository);
        paydayTransaction.execute();

        validateHourlyPaycheck(paydayTransaction, 2, payDate, 0.0);
    }

    @Test
    @DisplayName("시간제 직원에게 임금 지급 - 타임카드 하나")
    @Transactional
    void test_pay_single_hourly_employee_one_timecard() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;
        AddEmployeeTransaction transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        long payDate = 20011109;

        TimeCardTransaction timeCardTransaction =
                new TimeCardTransaction(payDate, 2.0, 2);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        PaydayTransaction paydayTransaction =
                new PaydayTransaction(payDate);
        paydayTransaction.setEmployeeRepository(employeeRepository);
        paydayTransaction.execute();

        validateHourlyPaycheck(paydayTransaction, 2, payDate, 30.5);
    }

    @Test
    @DisplayName("시간제 직원에게 임금 지급 - 타임카드 하나, 초과근무")
    @Transactional
    void test_single_hourly_employee_overtime_one_timecard() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;
        AddEmployeeTransaction transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        long payDate = 20011109;

        TimeCardTransaction timeCardTransaction =
                new TimeCardTransaction(payDate, 9.0, 2);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        PaydayTransaction paydayTransaction =
                new PaydayTransaction(payDate);
        paydayTransaction.setEmployeeRepository(employeeRepository);
        paydayTransaction.execute();

        validateHourlyPaycheck(paydayTransaction, 2, payDate, (8 + 1.5) * 15.25);
    }

    @Test
    @DisplayName("시간제 직원에게 임금 지급 - 잘못된 요일")
    @Transactional
    void test_single_hourly_employee_on_wrong_date() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;
        AddEmployeeTransaction transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        long payDate = 20011108;

        TimeCardTransaction timeCardTransaction =
                new TimeCardTransaction(payDate, 9.0, 2);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        PaydayTransaction paydayTransaction =
                new PaydayTransaction(payDate);
        paydayTransaction.setEmployeeRepository(employeeRepository);
        paydayTransaction.execute();

        Paycheck pc = paydayTransaction.getPaycheck(empId);
        assertNull(pc);
    }

    @Test
    @DisplayName("시간제 직원에게 임금 지급 - 타임카드 두개")
    @Transactional
    void test_single_hourly_employee_two_timecards() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;
        AddEmployeeTransaction transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        long payDate = 20011108;

        TimeCardTransaction timeCardTransaction =
                new TimeCardTransaction(payDate, 2.0, empId);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        timeCardTransaction =
                new TimeCardTransaction(++payDate, 5.0, empId);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        PaydayTransaction paydayTransaction =
                new PaydayTransaction(payDate);
        paydayTransaction.setEmployeeRepository(employeeRepository);
        paydayTransaction.execute();

        validateHourlyPaycheck(paydayTransaction, empId, payDate, 7 * 15.25);
    }

    @Test
    @DisplayName("시간제 직원에게 임금 지급 - 타임카드 지급 주기")
    @Transactional
    void test_single_hourly_employee_with_timecards_spanning_two_pay_periods() {
        int empId = 2;
        String name = "Bill";
        String address = "Home";
        double hourlyRate = 15.25;
        AddEmployeeTransaction transaction =
                new AddHourlyEmployee(empId, name, address, hourlyRate);
        transaction.setEmployeeRepository(employeeRepository);
        transaction.execute();

        long payDate = 20011109;
        long dateInPreviousPayPeriod = 20011102;

        TimeCardTransaction timeCardTransaction =
                new TimeCardTransaction(payDate, 2.0, empId);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        timeCardTransaction =
                new TimeCardTransaction(dateInPreviousPayPeriod, 5.0, empId);
        timeCardTransaction.setEmployeeRepository(employeeRepository);
        timeCardTransaction.execute();

        PaydayTransaction paydayTransaction =
                new PaydayTransaction(payDate);
        paydayTransaction.setEmployeeRepository(employeeRepository);
        paydayTransaction.execute();

        validateHourlyPaycheck(paydayTransaction, empId, payDate, 2 * 15.25);
    }

    private void validateHourlyPaycheck(
            PaydayTransaction paydayTransaction,
            int empId,
            long payDate,
            double pay
    ) {
        Paycheck pc = paydayTransaction.getPaycheck(empId);
        assertAll(
                () -> assertNotNull(pc),
                () -> assertEquals(DateUtils.toDate(payDate), pc.getPayPeriodEndDate()),
                () -> assertEquals(0.0, pc.getDeductions()),
                () -> assertEquals(pay, pc.getNetPay())
        );
    }

}