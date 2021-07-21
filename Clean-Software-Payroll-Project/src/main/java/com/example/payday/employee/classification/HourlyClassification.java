package com.example.payday.employee.classification;

import com.example.payday.Paycheck;
import com.example.payday.employee.PaymentClassification;
import com.example.payday.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("H")
public class HourlyClassification extends PaymentClassification {

    private Double hourlyRate;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TimeCard> timeCards = new ArrayList<>();

    public HourlyClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public void addTimeCard(TimeCard timeCard) {
        this.timeCards.add(timeCard);
    }

    public TimeCard getTimeCard(Long date) {
        return this.timeCards.stream()
                .filter(timeCard -> timeCard.getDate().equals(date))
                .findAny()
                .orElse(null);
    }

    @Override
    public double calculatePay(Paycheck pc) {
        double totalPay = 0;
        for(TimeCard timeCard : timeCards) {
            if (!isInPayPeriod(DateUtils.toDate(timeCard.getDate()), pc)) {
                continue;
            }
            totalPay += calculatePayForTimeCard(timeCard);
        }

        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard tc) {
        double hours = tc.getHours();
        double overtime = Math.max(0.0, hours - 8.0);
        double straightTime = hours - overtime;
        return straightTime * hourlyRate + overtime * hourlyRate * 1.5;
    }
}
