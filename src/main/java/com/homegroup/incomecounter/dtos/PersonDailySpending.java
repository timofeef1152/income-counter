package com.homegroup.incomecounter.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class PersonDailySpending {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    private long spending;

    public PersonDailySpending(LocalDate date, long spending) {
        this.date = date;
        this.spending = spending;
    }
}
