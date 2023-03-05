package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddReviewToClassRequest {

    private Integer classId;
    private Integer starsReview;
    private String textReview;

}
