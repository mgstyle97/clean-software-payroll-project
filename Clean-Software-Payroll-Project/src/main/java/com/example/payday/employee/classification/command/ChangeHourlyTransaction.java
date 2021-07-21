package com.example.payday.employee.classification.command;

import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.schedule.WeeklySchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {

    private double hourlyRate;

    public ChangeHourlyTransaction(int empId, double hourlyRate) {
        super(empId);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }

    @Override
    protected PaymentClassification getClassification() {
        return new HourlyClassification(hourlyRate);
    }
}
