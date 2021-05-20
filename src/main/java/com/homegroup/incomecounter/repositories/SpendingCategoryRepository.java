package com.homegroup.incomecounter.repositories;

import com.homegroup.incomecounter.models.SpendingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpendingCategoryRepository extends JpaRepository<SpendingCategory, UUID> {
}
