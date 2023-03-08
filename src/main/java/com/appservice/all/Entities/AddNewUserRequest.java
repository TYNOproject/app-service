package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddNewUserRequest {

    private String email;
    private String name;
    private Integer degree;
    private Integer facultyId;
    private Integer departmentId;
    private Integer year;
    private boolean isTeacher;
    private Double price;
    private String privateInfo;

    // getters and setters
}
