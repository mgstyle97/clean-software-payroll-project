package com.example.payday.employee.schedule;

import com.example.payday.employee.PaymentSchedule;
import com.example.payday.utils.DateUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;

@Entity
@DiscriminatorValue("B")
public class BiweeklySchedule extends PaymentSchedule {

    @Override
    public boolean isPayDate(Date payDate) {
        return false;
    }

    @Override
    public Date getPayPeriodStartDate(Date payPeriodEndDate) {
        super.setPreviousPayday(payPeriodEndDate);
        Calendar calendar = DateUtils.toCalendar(payPeriodEndDate);
        calendar.add(Calendar.DATE, -13);
        return calendar.getTime();
    }
}
