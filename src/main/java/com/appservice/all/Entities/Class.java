package com.appservice.all.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "classes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "stars_review")
    private Integer starsReview;

    @Column(name = "text_review")
    private String textReview;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;
    @Column(name = "is_over")
    private boolean isOver;

    public boolean isAvailable() {
        return this.status.equals("available");
    }
    public boolean isPending() {return this.status.equals("pending");}

    @PostLoad
    private void updateIsOver() {
        if (this.date.isBefore(LocalDate.now()) || (this.date.equals(LocalDate.now()) && this.getEndTime().isBefore(LocalTime.now()))) {
            this.isOver = true;
        }
    }
}
