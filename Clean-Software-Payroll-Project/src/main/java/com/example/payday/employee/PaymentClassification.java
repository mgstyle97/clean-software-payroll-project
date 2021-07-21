package com.example.payday.employee;

import com.example.payday.Paycheck;
import com.example.payday.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class PaymentClassification {

    @Id
    @GeneratedValue
    @Column(name = "classification_id")
    private Long id;

    public boolean isInPayPeriod(Date payDate, Paycheck pc) {
        return DateUtils.between(payDate, pc.getPayPeriodStartDate(), pc.getPayPeriodEndDate());
    }

    protected abstract double calculatePay(Paycheck pc);

}
