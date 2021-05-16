package com.homegroup.incomecounter.repositories;

import com.homegroup.incomecounter.dtos.PersonIncomeDto;
import com.homegroup.incomecounter.dtos.TransactionProjection;
import com.homegroup.incomecounter.models.Transaction;
import com.homegroup.incomecounter.models.TransactionSearch;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findAll(Specification<Transaction> specification);
    @Query(" SELECT p.name as person,\n" +
            "    sum(t.amount) AS spending\n" +
            "   FROM Transaction t\n" +
            "     LEFT JOIN Person p ON t.person.id = p.id\n" +
            "  GROUP BY p.id\n" +
            " HAVING sum(t.amount) <= :#{#search.maxAmount}")
    List<TransactionProjection> test(@Param("search") TransactionSearch search);
}