package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdatePersonalDetailsRequest {
    private Long studentId;
    private Long faculty;
    private Long department;
    private Integer degree;
    private Integer year;
    private String privateInfo;

    // getters and setters
}
