package com.example.payday.employee.affiliation;

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
@Table(name = "service_charge")
public class ServiceCharge {

    @Id
    Long date;

    private Double charge;

}
