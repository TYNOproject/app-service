package com.appservice.all.Entities;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "classes")
@Data
@Builder
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "stars_review")
    private Integer starsReview;

    @Column(name = "text_review")
    private String textReview;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "teacher_id")
    private Integer teacherId;

    @Column(name = "student_id")
    private Integer studentId;

    public boolean isAvailable() {
        return this.status.equals("available");
    }
}
