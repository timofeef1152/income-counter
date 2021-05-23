package com.homegroup.incomecounter.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
public class PersonSpendingInPeriod {
    @JsonIgnore
    private final UUID id;
    private final String person;
    private final long spending;

    public PersonSpendingInPeriod(String person) {
        id = null;
        this.person = person;
        spending = 0L;
    }

    public PersonSpendingInPeriod(UUID id, String person, Long spending) {
        this.id = id;
        this.person = person;
        if (Objects.nonNull(spending)) {
            this.spending = spending;
        } else {
            this.spending = 0;
        }
    }
}
