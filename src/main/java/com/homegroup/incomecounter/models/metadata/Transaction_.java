package com.homegroup.incomecounter.models.metadata;

import com.homegroup.incomecounter.models.Person;
import com.homegroup.incomecounter.models.SpendingCategory;
import com.homegroup.incomecounter.models.Transaction;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transaction.class)
public class Transaction_ {
    public static volatile SingularAttribute<Transaction, UUID> id;
    public static volatile SingularAttribute<Transaction, Person> person;
    public static volatile SingularAttribute<Transaction, LocalDate> date;
    public static volatile SingularAttribute<Transaction, SpendingCategory> category;
    public static volatile SingularAttribute<Transaction, String> comment;
    public static volatile SingularAttribute<Transaction, Long> amount;

    public static final String ID = "id";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String CATEGORY = "category";
    public static final String COMMENT = "comment";
    public static final String AMOUNT = "amount";
}