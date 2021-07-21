package com.example.payday.employee.method.command;

import com.example.payday.employee.PaymentMethod;
import com.example.payday.employee.method.DirectMethod;

public class ChangeDirectTransaction extends ChangeMethodTransaction {

    private String bank;
    private String account;

    public ChangeDirectTransaction(int empId, String bank, String account) {
        super(empId);
        this.bank = bank;
        this.account = account;
    }

    @Override
    protected PaymentMethod getMethod() {
        return new DirectMethod(bank, account);
    }
}
