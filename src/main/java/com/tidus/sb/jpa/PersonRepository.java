package com.tidus.sb.jpa;

import com.tidus.sb.jpa.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
