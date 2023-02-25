package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String email;
    private String name;
    private Integer degree;
    private Integer departmentId;
    private Integer year;
    private boolean isTeacher;
    private Double price;
    private String privateInfo;

    // getters and setters
}
