package com.tidus.sb.jpa.service;

import com.tidus.sb.jpa.bean.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
