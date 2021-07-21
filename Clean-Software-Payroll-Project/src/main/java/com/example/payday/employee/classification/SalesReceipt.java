package com.example.payday.employee.classification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "sales_receipt")
public class SalesReceipt {

    @Id
    private Long date;

    private Double amount;

}
