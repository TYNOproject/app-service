package com.appservice.all.Entities;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "department_name", nullable = false)
    private String departmentName;
}
