package com.appservice.all.Entities.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCoursesByCourseNameRequest {
    private String courseName;
}
