package com.example.payday.employee;

import com.example.payday.Paycheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class PaymentMethod {

    @Id
    @GeneratedValue
    @Column(name = "method_id")
    private Long id;

    protected abstract void pay(Paycheck pc);

}
