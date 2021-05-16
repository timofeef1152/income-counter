package com.homegroup.incomecounter.repositories;

import com.homegroup.incomecounter.models.TransactionSearch;
import com.homegroup.incomecounter.models.Transaction;
import com.homegroup.incomecounter.models.metadata.Transaction_;
import com.homegroup.incomecounter.models.metadata.Person_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonIncomeSpecification implements Specification<Transaction> {
    private final TransactionSearch search;

    public PersonIncomeSpecification(TransactionSearch search) {
        this.search = search;
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.greaterThanOrEqualTo(root.get(Transaction_.DATE), search.getStartDate()));
        predicates.add(cb.lessThanOrEqualTo(root.get(Transaction_.DATE), search.getEndDate()));
        if (Objects.nonNull(search.getCategoryFilter()) && search.getCategoryFilter().length > 0) {
            for (String category : search.getCategoryFilter()) {
                predicates.add(cb.equal(root.get(Transaction_.CATEGORY), category));
            }
        }
        if (Objects.nonNull(search.getCommentFilter()) && !search.getCommentFilter().isBlank()) {
            predicates.add(cb.like(root.get(Transaction_.COMMENT), search.getCommentFilter()));
        }
        if (Objects.nonNull(search.getMinAmount())) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Transaction_.AMOUNT), search.getMinAmount()));
        }
        if (Objects.nonNull(search.getMaxAmount())) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Transaction_.AMOUNT), search.getMaxAmount()));
        }
        final int size = predicates.size();
        criteriaQuery.groupBy(root.get(Transaction_.ID));
//        criteriaQuery.groupBy(root.get(Transaction_.PERSON).get(Person_.NAME), cb.sum(root.get(Transaction_.AMOUNT)));
        return cb.and(predicates.toArray(new Predicate[size]));
    }
}
