package com.appservice.all.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "courses")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "course_name", nullable = false)
    private String courseName;
}
