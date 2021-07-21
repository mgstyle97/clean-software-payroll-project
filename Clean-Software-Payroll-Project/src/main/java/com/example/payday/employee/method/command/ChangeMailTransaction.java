package com.example.payday.employee.method.command;

import com.example.payday.employee.PaymentMethod;
import com.example.payday.employee.method.MailMethod;

public class ChangeMailTransaction extends ChangeMethodTransaction {

    private String address;

    public ChangeMailTransaction(int empId, String address) {
        super(empId);
        this.address = address;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new MailMethod(address);
    }
}
