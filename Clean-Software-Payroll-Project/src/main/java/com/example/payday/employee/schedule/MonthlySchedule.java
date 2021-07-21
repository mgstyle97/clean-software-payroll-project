package com.example.payday.employee.schedule;

import com.example.payday.employee.PaymentSchedule;
import com.example.payday.utils.DateUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;

@Entity
@DiscriminatorValue("M")
public class MonthlySchedule extends PaymentSchedule {

    @Override
    public boolean isPayDate(Date payDate) {
        return isLastDayOfMonth(payDate);
    }

    @Override
    public Date getPayPeriodStartDate(Date payPeriodEndDate) {
        super.setPreviousPayday(payPeriodEndDate);
        Calendar calendar = DateUtils.toCalendar(payPeriodEndDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    private boolean isLastDayOfMonth(Date payDate) {
        Calendar calendar = DateUtils.toCalendar(payDate);
        int month = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DATE, 1);
        int monthOfAddedOneDay = calendar.get(Calendar.MONTH);
        return month != monthOfAddedOneDay;
    }
}
