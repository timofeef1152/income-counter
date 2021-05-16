package com.homegroup.incomecounter.setup;

import com.homegroup.incomecounter.models.Person;
import com.homegroup.incomecounter.models.SpendingCategory;
import com.homegroup.incomecounter.models.Transaction;
import com.homegroup.incomecounter.repositories.PersonRepository;
import com.homegroup.incomecounter.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
public class Initialization {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostConstruct
    @Transactional
    public void init1() {
        Person person = new Person();
        person.setName("Alex");
        Person person1 = personRepository.save(person);

        for (int i = 0; i < 10; i++) {
            Transaction transaction = new Transaction();
            transaction.setPerson(person1);
            transaction.setDate(LocalDate.now());
            transaction.setAmount(15000);
            transaction.setComment("Delete version!");
            transactionRepository.save(transaction);
        }
    }
}
