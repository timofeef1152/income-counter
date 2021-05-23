package com.homegroup.incomecounter.init;

import com.homegroup.incomecounter.models.Person;
import com.homegroup.incomecounter.models.SpendingCategory;
import com.homegroup.incomecounter.models.Transaction;
import com.homegroup.incomecounter.repositories.PersonRepository;
import com.homegroup.incomecounter.repositories.SpendingCategoryRepository;
import com.homegroup.incomecounter.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class Initialization {
    @Value("${init-enable}")
    private boolean initEnabled;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SpendingCategoryRepository categoryRepository;

    @PostConstruct
    @Transactional
    public void init1() {
        if (!initEnabled) {
            return;
        }
        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(2021, 1, 12));
        dates.add(LocalDate.of(2021, 5, 20));
        dates.add(LocalDate.of(2021, 7, 22));
        dates.add(LocalDate.of(1996, 7, 22));
        dates.add(LocalDate.of(2020, 12, 31));

        if (categoryRepository.findAll().size() < 1) {
            SpendingCategory category1 = new SpendingCategory();
            category1.setName("Food");

            SpendingCategory category2 = new SpendingCategory();
            category2.setName("Study");

            SpendingCategory category3 = new SpendingCategory();
            category3.setName("Tax");

            SpendingCategory category4 = new SpendingCategory();
            category4.setName("Transport");

            SpendingCategory category5 = new SpendingCategory();
            category5.setName("Credit");

            categoryRepository.saveAll(List.of(category1, category2, category3, category4, category5));
        }

        List<SpendingCategory> categories = categoryRepository.findAll();

        if (personRepository.findAll().size() < 99) {
            for (int y = 0; y < 100; y++) {
                final String secondName = UUID.randomUUID().toString().substring(0, 4);

                Person person = new Person();
                person.setName("Alex " + secondName);
                Person person1 = personRepository.save(person);

                int count = (int) (new Random(System.nanoTime()).nextDouble() * 100) + 1;
                if (count < 0) {
                    count = -1 * count;
                }

                for (int i = 0; i < count; i++) {
                    final int categoryId = Math.abs(Math.abs((int) (new Random(System.nanoTime()).nextDouble() * 10) - 5) - 1);

                    int value = (int) (new Random(System.nanoTime()).nextDouble() * 1000);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final double sign = new Random(System.nanoTime()).nextDouble();
                    if (sign <= 0.5) {
                        value = -1 * value;
                    }

                    SpendingCategory category = categories.get(categoryId);

                    Transaction transaction = new Transaction();
                    transaction.setPerson(person1);
                    transaction.setDate(dates.get(categoryId));
                    transaction.setAmount(value);
                    transaction.setComment(UUID.randomUUID().toString().substring(0, 4));
                    transaction.setCategory(category);
                    transactionRepository.save(transaction);

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                personRepository.save(person1);
            }
        }
    }
}
