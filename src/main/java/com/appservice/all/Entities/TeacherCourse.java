package com.appservice.all.Entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teacher_courses")
@Data
public class TeacherCourse {
    @Id
    private Integer id;
    @Column(name = "teacher_id")
    private Integer teacherId;
    @Column(name = "course_id")
    private Integer courseId;

}
