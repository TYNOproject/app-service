package com.appservice.all.controllers;

import com.appservice.all.Entities.Course;
import com.appservice.all.Entities.Requests.*;
import com.appservice.all.Entities.TeacherCourse;
import com.appservice.all.Entities.User;
import com.appservice.all.services.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api/db/classes")
public class ClassesController {

    public static final String AVAILABLE = "available";
    @Autowired
    DatabaseService service;

    @PostMapping("/add")
    public ResponseEntity<?> addNewClass(@RequestBody AddNewClassRequest addNewClassRequest) {
        Long courseId = addNewClassRequest.getCourseId();
        Long teacherId = addNewClassRequest.getTeacherId();
        Date date = addNewClassRequest.getDate();
        LocalTime startTime = addNewClassRequest.getStartTime();
        LocalTime endTime = addNewClassRequest.getEndTime();
        String status = AVAILABLE;
        service.saveClass(courseId, teacherId,date, startTime, endTime, status);

        return ResponseEntity.ok("Class added successfully");
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookClass(@RequestBody BookClassRequest bookClassRequest) {
        Long classId = bookClassRequest.getClassId();
        Long studentId = bookClassRequest.getStudentId();
        String response = service.bookClass(classId, studentId);
        if (response.equals("Class booked successfully"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> bookClass(@RequestBody AddReviewToClassRequest addReviewToClassRequest) {
        Long classId = addReviewToClassRequest.getClassId();
        String textReview = addReviewToClassRequest.getTextReview();
        Integer starsReview = addReviewToClassRequest.getStarsReview();
        boolean response = service.addReview(classId, textReview, starsReview);
        if (response)
            return ResponseEntity.ok("Review added successfully");
        else
            return ResponseEntity.badRequest().body("Request failed");
    }

}
