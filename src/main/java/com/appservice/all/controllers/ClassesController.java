package com.appservice.all.controllers;

import com.appservice.all.Entities.Requests.*;
import com.appservice.all.services.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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
        LocalDate date = addNewClassRequest.getDate();
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
        if (response.equals("Requested booking successfully, waiting for teacher confirmation"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approveClass(@RequestBody TeacherClassRequest teacherClassRequest) {
        Long classId = teacherClassRequest.getClassId();
        Long teacherId = teacherClassRequest.getTeacherId();
        String response = service.approveClass(classId, teacherId);
        if (response.equals("Successfully booked class"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectClass(@RequestBody TeacherClassRequest teacherClassRequest) {
        Long classId = teacherClassRequest.getClassId();
        Long teacherId = teacherClassRequest.getTeacherId();
        String response = service.rejectClass(classId, teacherId);
        if (response.equals("Successfully rejected class request"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/addReview")
    public ResponseEntity<?> addReview(@RequestBody AddReviewToClassRequest addReviewToClassRequest) {
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
