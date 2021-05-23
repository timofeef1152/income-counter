package com.homegroup.incomecounter.controllers;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;
import com.homegroup.incomecounter.services.PersonSpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.homegroup.incomecounter.util.StringConstants.*;

@RestController
@RequestMapping(value = MYAPP,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MyAppController {
    private final PersonSpendingService personSpendingService;

    @Autowired
    public MyAppController(PersonSpendingService personIncomeService) {
        this.personSpendingService = personIncomeService;
    }

    @GetMapping(SPENDING_IN_PERIOD)
    public List<PersonSpendingInPeriod> readPersonSpendingInPeriod(@Valid PersonSpendingInPeriodSearch search) {
        return personSpendingService.readPersonSpendingInPeriod(search);
    }

    @GetMapping(DAILY_SPENDING)
    public List<PersonDailySpending> readPersonDailySpending(@Valid PersonDailySpendingSearch search) {
        return personSpendingService.readPersonDailySpending(search);
    }
}