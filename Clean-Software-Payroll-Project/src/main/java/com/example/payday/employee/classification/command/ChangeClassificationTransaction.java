package com.example.payday.employee.classification.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.PaymentMethod;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.command.ChangeEmployeeTransaction;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

    public ChangeClassificationTransaction(int empId) {
        super(empId);
    }

    @Override
    protected final void change(Employee employee) {
        employee.setClassification(getClassification());
        employee.setSchedule(getSchedule());
    }

    protected abstract PaymentSchedule getSchedule();

    protected abstract PaymentClassification getClassification();

}
