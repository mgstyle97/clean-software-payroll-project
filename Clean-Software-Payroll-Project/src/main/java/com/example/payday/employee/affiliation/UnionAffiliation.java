package com.example.payday.employee.affiliation;


import com.example.payday.Paycheck;
import com.example.payday.utils.DateUtils;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Entity
@DiscriminatorValue("U")
public class UnionAffiliation extends Affiliation {

    private Double dues;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ServiceCharge> serviceCharges = new ArrayList<>();

    public UnionAffiliation(double dues) {
        this.dues = dues;
    }

    public void addServiceCharge(ServiceCharge serviceCharge) {
        this.serviceCharges.add(serviceCharge);
    }

    public ServiceCharge getServiceCharge(long date) {
        return this.serviceCharges.stream()
                .filter(serviceCharge -> serviceCharge.getDate().equals(date))
                .findAny()
                .orElse(null);
    }

    @Override
    public double calculateDeductions(Paycheck pc) {
        int fridayCount = numberOfFridayCountInPeriod(pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate());
        return dues * fridayCount + getTotalServiceCharge(pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate());
    }

    private double getTotalServiceCharge(Date payPeriodStartDate, Date payPeriodEndDate) {
        return this.serviceCharges.stream()
                .filter(sc -> DateUtils.between(DateUtils.toDate(sc.getDate()), payPeriodStartDate, payPeriodEndDate))
                .mapToDouble(ServiceCharge::getCharge)
                .sum();
    }

    private int numberOfFridayCountInPeriod(Date startDate, Date endDate) {
        int count = 0;
        Calendar startCalendar = DateUtils.toCalendar(startDate);
        Calendar endCalendar = DateUtils.toCalendar(endDate);
        while (startCalendar.compareTo(endCalendar) <= 0) {
            if (startCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                count++;
            }
            startCalendar.add(Calendar.DATE, 1);
        }
        return count;
    }
}
