package com.example.payday.employee.schedule.command;

import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.schedule.WeeklySchedule;

public class ChangeWeeklyTransaction extends ChangeScheduleTransaction {

    public ChangeWeeklyTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new WeeklySchedule();
    }
}
