package com.example.payday.employee.command;

import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.classification.CommissionedClassification;
import com.example.payday.employee.schedule.BiweeklySchedule;

public class AddCommissionedEmployee extends AddEmployeeTransaction {

    private double salary;
    private double commissionRate;

    public AddCommissionedEmployee(Integer empId, String name, String address, double salary, double commissionRate) {
        super(empId, name, address);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    public PaymentClassification getClassification() {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
