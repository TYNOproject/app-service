package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdatePersonalDetailsRequest {
    private Integer studentId;
    private String privateInfo;

    // getters and setters
}
