package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendTeacherRequest {
    private List<Long> coursesIds;
    private Double price;
    private String privateInfo;
}
