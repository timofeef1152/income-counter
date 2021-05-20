package com.homegroup.incomecounter.controllers;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;
import com.homegroup.incomecounter.services.PersonSpendingService;
import com.homegroup.incomecounter.services.PersonSpendingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/myapp",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MyAppController {
    private final PersonSpendingService personSpendingService;

    @Autowired
    public MyAppController(PersonSpendingServiceImpl personIncomeService) {
        this.personSpendingService = personIncomeService;
    }

    @GetMapping("spending-in-period")
    public List<PersonSpendingInPeriod> readPersonSpendingInPeriod(@Valid PersonSpendingInPeriodSearch search) {
        return personSpendingService.readPersonSpending(search);
    }

    @GetMapping("daily-spending")
    public List<PersonDailySpending> readPersonDailySpending(@Valid PersonDailySpendingSearch search) {
        return personSpendingService.readPersonDailySpending(search);
    }
}