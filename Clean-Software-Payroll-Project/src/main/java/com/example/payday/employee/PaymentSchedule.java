package com.example.payday.employee;

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
public abstract class PaymentSchedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date previousPayday;

    public abstract boolean isPayDate(Date payDate);
    public abstract Date getPayPeriodStartDate(Date payPeriodEndDate);

}
