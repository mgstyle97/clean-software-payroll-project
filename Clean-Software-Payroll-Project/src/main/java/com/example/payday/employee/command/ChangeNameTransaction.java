package com.example.payday.employee.command;

import com.example.payday.Transaction;
import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {

    private String name;

    public ChangeNameTransaction(int empId, String name) {
        super(empId);
        this.name = name;
    }

    @Override
    public void change(Employee employee) {
        employee.setName(name);
    }

}
