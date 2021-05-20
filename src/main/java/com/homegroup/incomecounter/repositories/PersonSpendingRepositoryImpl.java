package com.homegroup.incomecounter.repositories;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.Person;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;
import com.homegroup.incomecounter.models.Transaction;
import com.homegroup.incomecounter.models.metadata.Person_;
import com.homegroup.incomecounter.models.metadata.SpendingCategory_;
import com.homegroup.incomecounter.models.metadata.Transaction_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PersonSpendingRepositoryImpl implements PersonSpendingRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public PersonSpendingRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public List<PersonSpendingInPeriod> findAllPersonSpendingInPeriod(PersonSpendingInPeriodSearch search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<PersonSpendingInPeriod> cq = cb.createQuery(PersonSpendingInPeriod.class);
        Root<Transaction> transaction = cq.from(Transaction.class);
        Join<Transaction, Person> person = transaction.join(Transaction_.PERSON);
        Predicate[] predicates = createPredicatesForSpendingInPeriodSearch(transaction, cb, search);

        cq.where(predicates)
                .multiselect(person.get(Person_.ID), person.get(Person_.NAME), cb.sum(transaction.get(Transaction_.AMOUNT)))
                .groupBy(person.get(Person_.ID), person.get(Person_.NAME));
        final List<PersonSpendingInPeriod> list = entityManager.createQuery(cq).getResultList();

        List<UUID> uuids = list.stream()
                .map(PersonSpendingInPeriod::getId)
                .collect(Collectors.toList());

        CriteriaQuery<String> cq1 = cb.createQuery(String.class);
        Root<Person> person1 = cq1.from(Person.class);

        if (uuids.size() > 0) {
            cq1.where(cb.not(person1.get(Person_.ID).in(uuids)));
        }
        cq1.select(person1.get(Person_.NAME));

        final List<String> list1 = entityManager.createQuery(cq1).getResultList();

        final List<PersonSpendingInPeriod> result = new ArrayList<>(list);
        result.addAll(list1.stream()
                .map(PersonSpendingInPeriod::new)
                .collect(Collectors.toList()));
        return result;
    }

    @Transactional
    @Override
    public List<PersonDailySpending> findAllPersonDailySpending(PersonDailySpendingSearch search) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<PersonDailySpending> cq = cb.createQuery(PersonDailySpending.class);
        Root<Transaction> transaction = cq.from(Transaction.class);

        cq.multiselect(transaction.get(Transaction_.DATE), cb.sum(transaction.get(Transaction_.AMOUNT)))
                .where(createPredicateForDailySpendingSearch(transaction, cb, search))
                .groupBy(transaction.get(Transaction_.DATE))
                .orderBy(cb.asc(transaction.get(Transaction_.DATE)));
        return entityManager.createQuery(cq).getResultList();
    }

    private Predicate createPredicateForDailySpendingSearch(Root<Transaction> root, CriteriaBuilder cb, PersonDailySpendingSearch search) {
        return cb.and(cb.equal(root.get(Transaction_.PERSON).get(Person_.ID), search.getPerson()),
                cb.between(root.get(Transaction_.DATE), search.getStartDate(), search.getEndDate()));
    }

    private Predicate[] createPredicatesForSpendingInPeriodSearch(Root<Transaction> root, CriteriaBuilder cb, PersonSpendingInPeriodSearch search) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.between(root.get(Transaction_.DATE), search.getStartDate(), search.getEndDate()));
        if (Objects.nonNull(search.getCategoryFilter()) && search.getCategoryFilter().length > 0) {
            List<Predicate> categoryPredicates = new ArrayList<>();
            for (String category : search.getCategoryFilter()) {
                categoryPredicates.add(cb.equal(cb.lower(root.get(Transaction_.CATEGORY).get(SpendingCategory_.NAME)), category.trim().toLowerCase()));
            }
            final int categoryPredicatesSize = categoryPredicates.size();
            predicates.add(cb.or(categoryPredicates.toArray(new Predicate[categoryPredicatesSize])));
        }
        if (Objects.nonNull(search.getCommentFilter()) && !search.getCommentFilter().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get(Transaction_.COMMENT)), "%" + search.getCommentFilter().trim().toLowerCase() + "%"));
        }
        if (Objects.nonNull(search.getMinAmount())) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Transaction_.AMOUNT), search.getMinAmount()));
        }
        if (Objects.nonNull(search.getMaxAmount())) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Transaction_.AMOUNT), search.getMaxAmount()));
        }
        final int size = predicates.size();
        return predicates.toArray(new Predicate[size]);
    }
}
