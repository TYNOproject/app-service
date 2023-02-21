package com.appservice.all.Entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test")
@Data
public class User {
    @Id
    Integer id;
    String name;
}
