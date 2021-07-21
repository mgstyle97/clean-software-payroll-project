package com.example.payday.employee.classification.command;

import com.example.payday.Transaction;
import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.classification.CommissionedClassification;
import com.example.payday.employee.classification.SalesReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public class SalesReceiptTransaction implements Transaction {

    private long date;
    private double amount;
    private int empId;

    private EmployeeRepository employeeRepository;

    public SalesReceiptTransaction(long date, double amount, int empId) {
        this.date = date;
        this.amount = amount;
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
            CommissionedClassification cc = (CommissionedClassification) employee.getClassification();
            cc.addSalesReceipt(new SalesReceipt(date, amount));
            employeeRepository.save(employee);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Tried add sales receipt to non-commissioned employee");
        }
    }
}
