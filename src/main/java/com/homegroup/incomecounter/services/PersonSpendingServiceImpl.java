package com.homegroup.incomecounter.services;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;
import com.homegroup.incomecounter.repositories.PersonRepository;
import com.homegroup.incomecounter.repositories.PersonSpendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PersonSpendingServiceImpl implements PersonSpendingService {
    private final PersonSpendingRepository spendingRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PersonSpendingServiceImpl(PersonSpendingRepository spendingRepository,
                                     PersonRepository personRepository) {
        this.spendingRepository = spendingRepository;
        this.personRepository = personRepository;
    }

    public List<PersonSpendingInPeriod> readPersonSpendingInPeriod(PersonSpendingInPeriodSearch search) {
        return spendingRepository.findAllPersonSpendingInPeriod(search);
    }

    @Transactional
    @Override
    public List<PersonDailySpending> readPersonDailySpending(PersonDailySpendingSearch search) throws EntityNotFoundException {
        if (!personRepository.existsById(search.getPerson())){
            throw new EntityNotFoundException("Person doesn't exist.");
        }
        return spendingRepository.findAllPersonDailySpending(search);
    }
}
