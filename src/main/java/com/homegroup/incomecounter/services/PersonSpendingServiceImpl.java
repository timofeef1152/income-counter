package com.homegroup.incomecounter.services;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;
import com.homegroup.incomecounter.repositories.PersonSpendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonSpendingServiceImpl implements PersonSpendingService {
    private final PersonSpendingRepository repository;

    @Autowired
    public PersonSpendingServiceImpl(PersonSpendingRepository spendingRepository) {
        this.repository = spendingRepository;
    }

    public List<PersonSpendingInPeriod> readPersonSpending(PersonSpendingInPeriodSearch search) {
        return repository.findAllPersonSpendingInPeriod(search);
    }

    @Override
    public List<PersonDailySpending> readPersonDailySpending(PersonDailySpendingSearch search) {
        return repository.findAllPersonDailySpending(search);
    }
}
