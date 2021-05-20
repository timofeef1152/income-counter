package com.homegroup.incomecounter.repositories;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonSpendingRepository {
    List<PersonSpendingInPeriod> findAllPersonSpendingInPeriod(PersonSpendingInPeriodSearch search);

    List<PersonDailySpending> findAllPersonDailySpending(PersonDailySpendingSearch search);
}
