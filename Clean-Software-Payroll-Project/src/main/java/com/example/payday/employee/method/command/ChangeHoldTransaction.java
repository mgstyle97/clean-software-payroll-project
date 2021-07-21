package com.example.payday.employee.method.command;

import com.example.payday.employee.PaymentMethod;
import com.example.payday.employee.method.HoldMethod;

public class ChangeHoldTransaction extends ChangeMethodTransaction {

    public ChangeHoldTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentMethod getMethod() {
        return new HoldMethod();
    }
}
