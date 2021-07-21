package com.example.payday.employee.affiliation.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.affiliation.Affiliation;
import com.example.payday.employee.command.ChangeEmployeeTransaction;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

    public ChangeAffiliationTransaction(int empId) {
        super(empId);
    }

    @Override
    protected final void change(Employee employee) {
        employee.setAffiliation(getAffiliation());
    }

    protected abstract Affiliation getAffiliation();
}
