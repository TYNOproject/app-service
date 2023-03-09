package com.appservice.all.controllers;

import com.appservice.all.Entities.*;
import com.appservice.all.services.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/db/departments")
public class DepartmentsController {

    @Autowired
    DatabaseService service;

    @GetMapping("/{departmentId}/courses")
    public ResponseEntity<List<Course>> getCoursesByDepartment(@PathVariable Long departmentId) {
        log.info("got a request with id:{}", departmentId);
        List<Course> courses = service.getCoursesByDepartmentName(departmentId);
        ResponseEntity<List<Course>> response;
        if (courses.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(courses);
        }
        return response;
    }
}
