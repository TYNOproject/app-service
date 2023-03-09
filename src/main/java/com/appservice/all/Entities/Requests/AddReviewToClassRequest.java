package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AddReviewToClassRequest {

    private Long classId;
    private Integer starsReview;
    private String textReview;

}
