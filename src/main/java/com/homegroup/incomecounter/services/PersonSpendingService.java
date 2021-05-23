package com.homegroup.incomecounter.services;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;

import java.util.List;

public interface PersonSpendingService {
    List<PersonSpendingInPeriod> readPersonSpendingInPeriod(PersonSpendingInPeriodSearch search);

    List<PersonDailySpending> readPersonDailySpending(PersonDailySpendingSearch search);
}
