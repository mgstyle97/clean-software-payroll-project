package com.example.payday.employee.command;

import com.example.payday.employee.Employee;

public class ChangeAddressTransaction extends ChangeEmployeeTransaction {

    private String address;

    public ChangeAddressTransaction(int empId, String address) {
        super(empId);
        this.address = address;
    }

    @Override
    protected void change(Employee employee) {
        employee.setAddress(address);
    }

}
