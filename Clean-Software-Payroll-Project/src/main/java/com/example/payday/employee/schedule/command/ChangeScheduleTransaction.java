package com.example.payday.employee.schedule.command;

import com.example.payday.employee.Employee;
import com.example.payday.employee.PaymentSchedule;
import com.example.payday.employee.command.ChangeEmployeeTransaction;

public abstract class ChangeScheduleTransaction extends ChangeEmployeeTransaction {

    public ChangeScheduleTransaction(int empId) {
        super(empId);
    }

    @Override
    protected final void change(Employee employee) {
        employee.setSchedule(getSchedule());
    }

    protected abstract PaymentSchedule getSchedule();
}
