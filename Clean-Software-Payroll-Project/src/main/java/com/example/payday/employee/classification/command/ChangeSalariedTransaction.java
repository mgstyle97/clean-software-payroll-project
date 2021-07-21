package com.example.payday.employee.classification.command;

import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.classification.SalariedClassification;
import com.example.payday.employee.schedule.MonthlySchedule;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {

    private double salary;

    public ChangeSalariedTransaction(int empId, double salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }

    @Override
    protected PaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }
}
