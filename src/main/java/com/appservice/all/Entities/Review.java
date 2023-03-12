package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    private String teacherName;
    private String studentName;
    private Long teacherId;
    private Long studentId;
    private String textReview;
    private Integer starsReview;
}
