package com.appservice.all.controllers;

import com.appservice.all.Entities.Course;
import com.appservice.all.Entities.Requests.GetCoursesByCourseNameRequest;
import com.appservice.all.Entities.Requests.SearchCoursesRequest;
import com.appservice.all.Entities.User;
import com.appservice.all.services.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/db/courses")
public class CoursesController {

    public static final String AVAILABLE = "available";
    @Autowired
    DatabaseService service;

    @PostMapping("/searchCourses")
    public ResponseEntity<List<Course>> searchCourses(@RequestBody SearchCoursesRequest searchCoursesRequest) {
        String courseName = searchCoursesRequest.getCourseName();
        Long departmentId = searchCoursesRequest.getDepartmentId();
        Integer year = searchCoursesRequest.getYear();

        List<Course> courses = service.searchCourses(courseName, departmentId, year);
        ResponseEntity<List<Course>> response;
        if (courses.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(courses);
        }
        return response;
    }


    @GetMapping("/{courseId}/teachers")
    public ResponseEntity<List<User>> getTeachersForCourse(@PathVariable Long courseId) {
        log.info("got a request with course id:{}", courseId);
        List<User> teachersOfCourse = service.getTeachersForCourse(courseId);
        ResponseEntity<List<User>> response;
        if (teachersOfCourse.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(teachersOfCourse);
        }
        return response;
    }


    @PostMapping("/getByName")
    public ResponseEntity<List<Course>> getCoursesByCourseName(@RequestBody GetCoursesByCourseNameRequest request) {
        List<Course> courses = service.getCoursesByCourseName(request.getCourseName());
        ResponseEntity<List<Course>> response;
        if (courses.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(courses);
        }
        return response;
    }

}
