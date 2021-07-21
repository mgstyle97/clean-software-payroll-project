package com.example.payday.employee.method;

import com.example.payday.Paycheck;
import com.example.payday.employee.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@DiscriminatorValue("H")
public class HoldMethod extends PaymentMethod {

    @Override
    public void pay(Paycheck pc) {

    }
}
