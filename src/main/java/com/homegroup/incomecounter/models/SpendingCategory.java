package com.homegroup.incomecounter.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "spending_categories",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
public class SpendingCategory extends BaseEntity {
    private String name;
}
