package com.homegroup.incomecounter.services;

import com.homegroup.incomecounter.dtos.PersonIncomeDto;
import com.homegroup.incomecounter.models.TransactionSearch;

import java.util.List;

public interface PersonIncomeService {
    List<PersonIncomeDto> readPeopleIncome(TransactionSearch filter);
}
