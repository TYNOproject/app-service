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
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "degree")
    private Integer degree;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "year")
    private Integer year;

    @Column(name = "is_teacher")
    private Boolean isTeacher;

    @Column(name = "price")
    private Double price;

    @Column(name = "private_info")
    private String privateInfo;
}
