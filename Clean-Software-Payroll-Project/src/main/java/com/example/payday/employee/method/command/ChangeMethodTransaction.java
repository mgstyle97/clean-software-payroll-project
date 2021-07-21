package com.example.payday.employee.method.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.PaymentMethod;
import com.example.payday.employee.command.ChangeEmployeeTransaction;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

    public ChangeMethodTransaction(int empId) {
        super(empId);
    }

    @Override
    protected final void change(Employee employee) {
        employee.setMethod(getMethod());
    }

    protected abstract PaymentMethod getMethod();
}
