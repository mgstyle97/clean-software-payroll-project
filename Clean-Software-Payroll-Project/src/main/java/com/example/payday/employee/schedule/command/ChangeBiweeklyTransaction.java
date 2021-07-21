package com.example.payday.employee.schedule.command;

import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.schedule.BiweeklySchedule;

public class ChangeBiweeklyTransaction extends ChangeScheduleTransaction {
    public ChangeBiweeklyTransaction(int empId) {
        super(empId);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return new BiweeklySchedule();
    }
}
