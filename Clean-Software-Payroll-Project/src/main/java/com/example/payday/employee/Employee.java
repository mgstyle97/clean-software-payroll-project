package com.example.payday.employee;

import com.example.payday.Paycheck;
import com.example.payday.employee.affiliation.Affiliation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "classification_id",
            referencedColumnName = "classification_id"
    )
    private PaymentClassification classification;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "schedule_id",
            referencedColumnName = "schedule_id"
    )
    private PaymentSchedule schedule;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            optional = false,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "method_id",
            referencedColumnName = "method_id"
    )
    private PaymentMethod method;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "affiliation_id",
            referencedColumnName = "affiliation_id"
    )
    private Affiliation affiliation;

    public Employee(Integer empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    public boolean isPayDate(Date payDate) {
        return this.schedule.isPayDate(payDate);
    }

    public Paycheck payday(Date payDate) {
        Paycheck pc = new Paycheck(getPayPeriodStartDate(payDate), payDate);
        double grossPay = classification.calculatePay(pc);
        double deductions = affiliation.calculateDeductions(pc);
        double netPay = grossPay - deductions;
        pc.setGrossPay(grossPay);
        pc.setDeductions(deductions);
        pc.setNetPay(netPay);
        this.method.pay(pc);

        return pc;
    }

    private Date getPayPeriodStartDate(Date payPeriodEndDate) {
        return this.schedule.getPayPeriodStartDate(payPeriodEndDate);
    }

}
