package com.homegroup.incomecounter.repositories;

import com.homegroup.incomecounter.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
}
