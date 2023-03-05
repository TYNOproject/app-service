package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddNewClassRequest {

    private Integer courseId;
    private Integer teacherId;
    private Integer studentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Integer starsReview;
    private String textReview;

    // getters and setters
}
