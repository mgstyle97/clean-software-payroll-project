package com.example.payday.employee.affiliation.command;

import com.example.payday.Transaction;
import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.employee.affiliation.ServiceCharge;
import com.example.payday.employee.affiliation.UnionAffiliation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class ServiceChargeTransaction implements Transaction {

    private int empId;
    private long date;
    private double charge;

    private EmployeeRepository employeeRepository;

    public ServiceChargeTransaction(int empId, long date, double charge) {
        this.empId = empId;
        this.date = date;
        this.charge = charge;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void execute() {
        Employee employee = employeeRepository
                .findOne(empId);
        try {
            UnionAffiliation uaf = (UnionAffiliation) employee.getAffiliation();
            uaf.addServiceCharge(new ServiceCharge(date, charge));
            employeeRepository.save(employee);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Tried add service charge no-affiliation employee");
        }
    }
}
