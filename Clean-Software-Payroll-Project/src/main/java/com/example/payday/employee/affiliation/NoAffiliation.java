package com.example.payday.employee.affiliation;

import com.example.payday.Paycheck;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("N")
public class NoAffiliation extends Affiliation {
    @Override
    public double calculateDeductions(Paycheck pc) {
        return 0;
    }
}
