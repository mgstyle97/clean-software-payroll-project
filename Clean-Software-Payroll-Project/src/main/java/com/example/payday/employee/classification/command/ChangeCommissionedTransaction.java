package com.example.payday.employee.classification.command;

import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.classification.CommissionedClassification;
import com.example.payday.employee.schedule.BiweeklySchedule;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {

    private double salary;
    private double commissionRate;

    public ChangeCommissionedTransaction(int empId, double salary, double commissionRate) {
        super(empId);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }

    @Override
    protected PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }
}
