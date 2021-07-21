package com.example.payday;

import com.example.payday.employee.Employee;
import com.example.payday.employee.EmployeeRepository;
import com.example.payday.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configurable
public class PaydayTransaction implements Transaction {

    private Date payDate;

    private final Map<Integer, Paycheck> paychecks = new HashMap<>();

    private EmployeeRepository employeeRepository;

    public PaydayTransaction(long payDate) {
        this.payDate = DateUtils.toDate(payDate);
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void execute() {
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            if (employee.isPayDate(payDate)) {
                Paycheck pc = employee.payday(payDate);
                paychecks.put(employee.getEmpId(), pc);
            }
        }
    }

    public Paycheck getPaycheck(int empId) {
        return paychecks.get(empId);
    }
}
