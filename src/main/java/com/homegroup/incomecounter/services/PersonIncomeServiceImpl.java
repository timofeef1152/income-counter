package com.homegroup.incomecounter.services;

import com.homegroup.incomecounter.dtos.PersonIncomeDto;
import com.homegroup.incomecounter.dtos.TransactionProjection;
import com.homegroup.incomecounter.models.Transaction;
import com.homegroup.incomecounter.models.TransactionSearch;
import com.homegroup.incomecounter.repositories.TransactionRepository;
import com.homegroup.incomecounter.repositories.PersonIncomeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonIncomeServiceImpl implements PersonIncomeService {
    private final TransactionRepository repository;

    @Autowired
    public PersonIncomeServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<PersonIncomeDto> readPeopleIncome(TransactionSearch search) {
        PersonIncomeSpecification specification = new PersonIncomeSpecification(search);
        List<Transaction> transactions = repository.findAll(specification);
        search.setMaxAmount(20000L);
        List<TransactionProjection> transactions2 = repository.test(search);


        throw new RuntimeException("Not implemented");
    }
}
