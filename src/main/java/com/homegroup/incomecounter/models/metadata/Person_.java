package com.homegroup.incomecounter.models.metadata;

import com.homegroup.incomecounter.models.Person;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public class Person_ {
    public static volatile SingularAttribute<Person, UUID> id;
    public static volatile SingularAttribute<Person, String> name;

    public static final String ID = "id";
    public static final String NAME = "name";
}