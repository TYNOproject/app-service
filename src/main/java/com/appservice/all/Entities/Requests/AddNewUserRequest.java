package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddNewUserRequest {
    private String email;
    private String password;
    private String name;
    private Integer degree;
    private Long faculty;
    private Long department;
    private Integer year;
    private boolean isTeacher;

}
