package com.example.payday.employee.classification.command;

import com.example.payday.Transaction;
import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.classification.TimeCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public class TimeCardTransaction implements Transaction {

    private long date;
    private double hours;
    private int empId;

    private EmployeeRepository employeeRepository;

    public TimeCardTransaction(long date, double hours, int empId) {
        this.date = date;
        this.hours = hours;
        this.empId = empId;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void execute() {
        Employee employee = employeeRepository
                .findOne(empId);

        try {
            HourlyClassification hc = (HourlyClassification) employee.getClassification();
            hc.addTimeCard(new TimeCard(date, hours));
            employeeRepository.save(employee);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Tried to add time card to no-hourly employee");
        }

    }

}
