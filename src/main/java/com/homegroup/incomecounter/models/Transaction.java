package com.homegroup.incomecounter.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class Transaction extends BaseEntity {
    @ManyToOne
    private Person person;
    private LocalDate date;
    @ManyToOne
    private SpendingCategory category;
    private String comment;
    private long amount;
}
