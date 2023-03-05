package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookClassRequest {
    private Integer classId;
    private Integer studentId;
}
