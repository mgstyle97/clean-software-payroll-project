package com.example.payday.employee.schedule.command;

import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.schedule.MonthlySchedule;

public class ChangeMonthlyTransaction extends ChangeScheduleTransaction {

    public ChangeMonthlyTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new MonthlySchedule();
    }
}
