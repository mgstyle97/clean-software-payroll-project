package com.example.payday.employee.schedule;

import com.example.payday.employee.PaymentSchedule;
import com.example.payday.utils.DateUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;

@Entity
@DiscriminatorValue("W")
public class WeeklySchedule extends PaymentSchedule {

    @Override
    public boolean isPayDate(Date payDate) {
        return isFriday(payDate);
    }

    @Override
    public Date getPayPeriodStartDate(Date payPeriodEndDate) {
        super.setPreviousPayday(payPeriodEndDate);
        Calendar calendar = DateUtils.toCalendar(payPeriodEndDate);
        calendar.add(Calendar.DATE, -6);
        return calendar.getTime();
    }

    private boolean isFriday(Date payDate) {
        Calendar calendar = DateUtils.toCalendar(payDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.FRIDAY;
    }
}
