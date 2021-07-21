package com.example.payday.employee.command;

import com.example.payday.Transaction;
import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public abstract class ChangeEmployeeTransaction implements Transaction {

    private int empId;

    private EmployeeRepository employeeRepository;

    public ChangeEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void execute() {
        Employee employee = employeeRepository.findOne(empId);
        change(employee);
        employeeRepository.save(employee);
    }

    protected abstract void change(Employee employee);
}
