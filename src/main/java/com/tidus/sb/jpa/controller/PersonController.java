package com.tidus.sb.jpa.controller;

import com.tidus.sb.jpa.bean.Person;
import com.tidus.sb.jpa.service.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * https://segmentfault.com/a/1190000037755804
 */
@RestController
@RequestMapping(value = "person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;


    @RequestMapping(path = "getPerson")
    public Object getPerson() {
        List<Person> list = personRepository.findAll();
        return list;
    }

    @RequestMapping(path = "addPerson")
    public void addPerson(Person person) {
        personRepository.save(person);
    }

    @RequestMapping(path = "delPerson")
    public void deletePerson(Long id) {
         personRepository.deleteById(id);
    }
}
