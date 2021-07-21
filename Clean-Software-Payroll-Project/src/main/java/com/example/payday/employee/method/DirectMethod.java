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
@Data
@AllArgsConstructor
@Entity
@DiscriminatorValue("D")
public class DirectMethod extends PaymentMethod {

    private String bank;
    private String account;

    @Override
    public void pay(Paycheck pc) {

    }
}
