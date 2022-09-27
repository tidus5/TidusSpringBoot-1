package com.tidus.sb.jpa.bean;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "age", length = 4)
    private int age;
}
