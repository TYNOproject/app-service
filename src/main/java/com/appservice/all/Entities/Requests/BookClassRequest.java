package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookClassRequest {
    private Long classId;
    private Long studentId;
}
