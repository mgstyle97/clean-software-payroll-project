package com.example.payday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Paycheck {

    private double grossPay;
    private double deductions;
    private double netPay;
    private Date payPeriodStartDate;
    private Date payPeriodEndDate;

    public Paycheck(Date payPeriodStartDate, Date payPeriodEndDate) {
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = payPeriodEndDate;
    }

}
