package com.homegroup.incomecounter.repositories;

import com.homegroup.incomecounter.IncomeCounterApplication;
import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IncomeCounterApplication.class)
@Transactional
public class PersonSpendingRepositoryTest {
    private final String catFood = "Food";
    private final String catStudy = "Study";
    private final String perAlex = "Alex";
    private final String perBrian = "Brian";
    private final LocalDate date1 = LocalDate.of(1996, 07, 22);
    private final LocalDate date2 = LocalDate.of(2018, 05, 15);
    private final LocalDate date3 = LocalDate.of(2021, 05, 21);
    private final LocalDate date4 = LocalDate.of(2021, 07, 22);
    private final long amount1 = -1000L;
    private final long amount2 = 600L;
    private final long amount3 = 7000L;
    private final long amount4 = -14500L;
    private final long amount5 = 10750L;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PersonSpendingRepository spendingRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SpendingCategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Before
    public void dbInit() {
        SpendingCategory category1 = new SpendingCategory();
        category1.setName(catFood);
        category1 = categoryRepository.save(category1);

        SpendingCategory category2 = new SpendingCategory();
        category2.setName(catStudy);
        category2 = categoryRepository.save(category2);

        //Person with 5 transactions
        Person person1 = new Person();
        person1.setName(perAlex);
        person1 = personRepository.save(person1);

        Person person2 = new Person();
        person2.setName(perBrian);
        person2 = personRepository.save(person2);

        Transaction transaction1 = new Transaction();
        transaction1.setPerson(person1);
        transaction1.setCategory(category1);
        transaction1.setDate(date1);
        transaction1.setAmount(amount1);
        transaction1.setComment("holiday");
        transaction1 = transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setPerson(person1);
        transaction2.setCategory(category2);
        transaction2.setDate(date2);
        transaction2.setAmount(amount2);
        transaction2.setComment("nothing");
        transaction2 = transactionRepository.save(transaction2);

        Transaction transaction3 = new Transaction();
        transaction3.setPerson(person1);
        transaction3.setCategory(category1);
        transaction3.setDate(date3);
        transaction3.setAmount(amount3);
        transaction3.setComment("yam-yam");
        transaction3 = transactionRepository.save(transaction3);

        Transaction transaction4 = new Transaction();
        transaction4.setPerson(person1);
        transaction4.setCategory(category1);
        transaction4.setDate(date3);
        transaction4.setAmount(amount4);
        transaction4.setComment("yam-yam");
        transaction4 = transactionRepository.save(transaction4);

        Transaction transaction5 = new Transaction();
        transaction5.setPerson(person1);
        transaction5.setCategory(category2);
        transaction5.setDate(date4);
        transaction5.setAmount(amount5);
        transaction5.setComment("holiday");
        transaction5 = transactionRepository.save(transaction5);
    }

    @Test
    @DisplayName("findAllPersonSpendingInPeriod by date successful")
    public void findAllPersonSpendingInPeriodByDateTest() {
        //given
        PersonSpendingInPeriodSearch search = new PersonSpendingInPeriodSearch();
        search.setStartDate(date1);
        search.setEndDate(date3);

        final long peopleCount = personRepository.count();
        final long sum = amount1 + amount2 + amount3 + amount4;

        //when
        List<PersonSpendingInPeriod> list = spendingRepository.findAllPersonSpendingInPeriod(search);

        //then
        Assert.assertEquals(sum, list.get(0).getSpending());
        Assert.assertEquals(peopleCount, list.size());
    }

    @Test
    @DisplayName("findAllPersonSpendingInPeriod by date and category successful")
    public void findAllPersonSpendingInPeriodByDateAndCategoryTest() {
        //given
        String[] categories = {catFood};
        PersonSpendingInPeriodSearch search = new PersonSpendingInPeriodSearch();
        search.setStartDate(date1);
        search.setEndDate(date3);
        search.setCategoryFilter(categories);

        final long peopleCount = personRepository.count();
        final long sum = amount1 + amount3 + amount4;

        //when
        List<PersonSpendingInPeriod> list = spendingRepository.findAllPersonSpendingInPeriod(search);

        //then
        Assert.assertEquals(sum, list.get(0).getSpending());
        Assert.assertEquals(peopleCount, list.size());
    }

    @Test
    @DisplayName("findAllPersonSpendingInPeriod by date and comment successful")
    public void findAllPersonSpendingInPeriodByDateAndCommentTest() {
        //given
        PersonSpendingInPeriodSearch search = new PersonSpendingInPeriodSearch();
        search.setStartDate(date1);
        search.setEndDate(date3);
        search.setCommentFilter("yam-yam");

        final long peopleCount = personRepository.count();
        final long sum = amount3 + amount4;

        //when
        List<PersonSpendingInPeriod> list = spendingRepository.findAllPersonSpendingInPeriod(search);

        //then
        Assert.assertEquals(sum, list.get(0).getSpending());
        Assert.assertEquals(peopleCount, list.size());
    }

    @Test
    @DisplayName("findAllPersonSpendingInPeriod by date and amount successful")
    public void findAllPersonSpendingInPeriodByDateAndAmountTest() {
        //given
        PersonSpendingInPeriodSearch search = new PersonSpendingInPeriodSearch();
        search.setStartDate(date1);
        search.setEndDate(date3);
        search.setMinAmount(0L);
        search.setMaxAmount(10_000L);

        final long peopleCount = personRepository.count();
        final long sum = amount2 + amount3;

        //when
        List<PersonSpendingInPeriod> list = spendingRepository.findAllPersonSpendingInPeriod(search);

        //then
        Assert.assertEquals(sum, list.get(0).getSpending());
        Assert.assertEquals(peopleCount, list.size());
    }

    @Test
    @DisplayName("findAllPersonDailySpending successful")
    public void findAllPersonDailySpendingTest() {
        //given
        UUID personId = personRepository.findAll().get(0).getId();
        PersonDailySpendingSearch search = new PersonDailySpendingSearch();
        search.setPerson(personId);
        search.setStartDate(date1);
        search.setEndDate(date3);

        //when
        List<PersonDailySpending> list = spendingRepository.findAllPersonDailySpending(search);

        //then
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(-7500, list.get(2).getSpending());
    }
}
