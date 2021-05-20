package com.homegroup.incomecounter.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
public class Transaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private SpendingCategory category;
    private String comment;
    private long amount;
}
