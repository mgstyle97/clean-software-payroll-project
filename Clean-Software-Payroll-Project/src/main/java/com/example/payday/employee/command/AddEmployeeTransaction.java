package com.example.payday.employee.command;

import com.example.payday.Transaction;
import com.example.payday.employee.*;
import com.example.payday.employee.affiliation.NoAffiliation;
import com.example.payday.employee.method.HoldMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public abstract class AddEmployeeTransaction implements Transaction {

    private final Integer empId;
    private final String name;
    private final String address;

    private EmployeeRepository employeeRepository;

    protected AddEmployeeTransaction(Integer empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void execute() {
        // template

    }

    // abstract method
    public abstract PaymentClassification getClassification();

    public abstract PaymentSchedule getSchedule();

}
