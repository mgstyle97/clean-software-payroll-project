package com.example.payday.employee.affiliation.command;

import com.example.payday.employee.affiliation.Affiliation;
import com.example.payday.employee.affiliation.UnionAffiliation;

public class ChangeAffiliatedTransaction extends ChangeAffiliationTransaction {

    private double dues;

    public ChangeAffiliatedTransaction(int empId, double dues) {
        super(empId);
        this.dues = dues;
    }

    @Override
    protected Affiliation getAffiliation() {
        return new UnionAffiliation(dues);
    }
}
