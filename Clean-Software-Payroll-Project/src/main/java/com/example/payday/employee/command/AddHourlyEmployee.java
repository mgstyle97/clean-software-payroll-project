package com.example.payday.employee.command;

import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.classification.HourlyClassification;
import com.example.payday.employee.schedule.WeeklySchedule;

public class AddHourlyEmployee extends AddEmployeeTransaction {

    private double hourlyRate;

    public AddHourlyEmployee(Integer empId, String name, String address, double hourlyRate) {
        super(empId, name, address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public PaymentClassification getClassification() {
        return null;
    }

    @Override
    public PaymentSchedule getSchedule() {
        return null;
    }
}
