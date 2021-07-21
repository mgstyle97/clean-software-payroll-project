package com.example.payday.employee.command;

import com.example.payday.employee.PaymentClassification;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.classification.SalariedClassification;
import com.example.payday.employee.schedule.MonthlySchedule;

public class AddSalariedEmployee extends AddEmployeeTransaction {

    private double salary;

    public AddSalariedEmployee(Integer empId, String name,
                               String address, double salary) {
        super(empId, name, address);
        this.salary = salary;
    }

    @Override
    public PaymentClassification getClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
