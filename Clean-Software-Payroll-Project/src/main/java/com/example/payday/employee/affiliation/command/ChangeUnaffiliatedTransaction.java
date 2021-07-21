package com.example.payday.employee.affiliation.command;

import com.example.payday.employee.affiliation.Affiliation;
import com.example.payday.employee.affiliation.NoAffiliation;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int empId) {
        super(empId);
    }

    @Override
    protected Affiliation getAffiliation() {
        return new NoAffiliation();
    }
}
