package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class AddNewClassRequest {

    private Long courseId;
    private Long teacherId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    // getters and setters
}
