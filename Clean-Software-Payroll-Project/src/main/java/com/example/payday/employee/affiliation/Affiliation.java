package com.example.payday.employee.affiliation;

import com.example.payday.Paycheck;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Affiliation {

    @Id
    @GeneratedValue
    @Column(name = "affiliation_id")
    private Long id;

    public abstract double calculateDeductions(Paycheck pc);

}
