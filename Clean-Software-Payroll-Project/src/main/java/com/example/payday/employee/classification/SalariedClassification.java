package com.example.payday.employee.classification;

import com.example.payday.Paycheck;
import com.example.payday.employee.PaymentClassification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@DiscriminatorValue("S")
public class SalariedClassification extends PaymentClassification {

    private Double salary;

    @Override
    public double calculatePay(Paycheck pc) {
        return salary;
    }
}
