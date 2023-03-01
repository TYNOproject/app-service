package com.appservice.all.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCoursesByDepartmentRequest {
    @JsonProperty("departmentId")
    private Integer departmentId;
}
