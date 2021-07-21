package com.example.payday.employee.command;

import com.example.payday.Transaction;
import com.example.payday.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
public class DeleteEmployeeTransaction implements Transaction {

    private Integer empId;
    private EmployeeRepository employeeRepository;

    public DeleteEmployeeTransaction(Integer empId) {
        this.empId = empId;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public void execute() {
        employeeRepository.deleteById(empId);
    }
}
