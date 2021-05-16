package com.homegroup.incomecounter.models.metadata;

import com.homegroup.incomecounter.models.SpendingCategory;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SpendingCategory.class)
public class SpendingCategory_ {
    public static volatile SingularAttribute<SpendingCategory, UUID> id;
    public static volatile SingularAttribute<SpendingCategory, String> name;

    public static final String ID = "id";
    public static final String NAME = "name";
}