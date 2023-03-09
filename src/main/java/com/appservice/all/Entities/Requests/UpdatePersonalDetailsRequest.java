package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdatePersonalDetailsRequest {
    private Long studentId;
    private String privateInfo;

    // getters and setters
}
