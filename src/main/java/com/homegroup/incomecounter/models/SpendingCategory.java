package com.homegroup.incomecounter.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "spending_category")
@Getter
@Setter
public class SpendingCategory extends BaseEntity {
    private String name;
}
