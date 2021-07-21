package com.example.payday.employee.classification;

import com.example.payday.Paycheck;
import com.example.payday.employee.PaymentClassification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("C")
public class CommissionedClassification extends PaymentClassification {

    private Double salary;
    private Double commissionRate;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SalesReceipt> salesReceipts = new ArrayList<>();

    public CommissionedClassification(double salary, double commissionRate) {
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        this.salesReceipts.add(salesReceipt);
    }

    public SalesReceipt getSalesReceipt(long date) {
        return this.salesReceipts.stream()
                .filter(salesReceipt -> salesReceipt.getDate().equals(date))
                .findAny()
                .orElse(null);
    }

    @Override
    public double calculatePay(Paycheck pc) {
        return 0;
    }
}
