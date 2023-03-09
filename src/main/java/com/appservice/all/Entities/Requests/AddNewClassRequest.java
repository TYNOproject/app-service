package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddNewClassRequest {

    private Long courseId;
    private Long teacherId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // getters and setters
}
