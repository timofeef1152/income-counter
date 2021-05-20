package com.homegroup.incomecounter.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "people")
@Getter
@Setter
public class Person extends BaseEntity {
    private String name;
}
