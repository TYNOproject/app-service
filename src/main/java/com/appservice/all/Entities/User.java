package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "faculty_id", nullable = false)
    private Integer facultyId;

    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Column(name = "degree", nullable = false)
    private Integer degree;
    @Column(name = "year", nullable = false)
    private Integer year;
    @Column(name = "is_teacher", nullable = false)
    private Boolean isTeacher;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "private_info", nullable = false)
    private String privateInfo;
}
