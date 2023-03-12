package com.appservice.all.controllers;

import com.appservice.all.Entities.Class;
import com.appservice.all.Entities.*;
import com.appservice.all.Entities.Requests.*;
import com.appservice.all.services.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api/db/users")
public class UsersController {

    @Autowired
    DatabaseService service;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody AddNewUserRequest addNewUserRequest) {
        String email = addNewUserRequest.getEmail();
        String name = addNewUserRequest.getName();
        Integer degree = addNewUserRequest.getDegree();
        Long faculty = addNewUserRequest.getFaculty();
        Long departmentId = addNewUserRequest.getDepartment();
        Integer year = addNewUserRequest.getYear();
        String password = addNewUserRequest.getPassword();
        service.saveUser(email, password, name, degree, faculty, departmentId, year, false);
        return ResponseEntity.ok("User added successfully");
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        return service.signIn(email, password);

    }


    @PostMapping("/updatePrivateInfo")
    public ResponseEntity<?> updatePrivateInfo(@RequestBody UpdatePersonalDetailsRequest updatePersonalDetailsRequest) {
        Long studentId = updatePersonalDetailsRequest.getStudentId();
        Long faculty = updatePersonalDetailsRequest.getFaculty();
        Long department = updatePersonalDetailsRequest.getDepartment();
        Integer degree = updatePersonalDetailsRequest.getDegree();
        Integer year = updatePersonalDetailsRequest.getYear();
        String privateInfo = updatePersonalDetailsRequest.getPrivateInfo();
        boolean response = service.updatePersonalDetails(studentId, faculty, department, degree, year, privateInfo);
        if (response)
            return ResponseEntity.ok("Successfully updated personal details");
        else
            return ResponseEntity.badRequest().body("Request failed");
    }

    @PostMapping("/setStudentAsTeacher")
    public ResponseEntity<?> bookClass(@RequestBody SetStudentAsTeacherRequest setStudentAsTeacherRequest) {
        Long studentId = setStudentAsTeacherRequest.getStudentId();
        boolean response = service.setStudentAsTeacher(studentId);
        if (response)
            return ResponseEntity.ok("Successfully signed student as teacher");
        else
            return ResponseEntity.badRequest().body("Request failed");
    }


    @PostMapping("/getTeachersContainingString")
    public ResponseEntity<List<User>> getTeachersContainingString(@RequestBody GetTeachersContainingStringRequest request) {
        List<User> users = service.getTeachersContainingString(request.getTeacherName());
        ResponseEntity<List<User>> response;
        if (users.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(users);
        }
        return response;
    }

    @PostMapping("/getTeachersByCourse")
    public ResponseEntity<List<User>> getTeachersByCourse(@RequestBody GetTeachersByCourseRequest getTeachersByCourseRequest) {
        Course course = service.getCourseByName(getTeachersByCourseRequest.getCourseName());
        if(course == null){
            return ResponseEntity.badRequest().body(null);
        }
        List<TeacherCourse> teacherCourses = service.getTeacherCoursesByCourse(course);
        List<Long> teachersIds = teacherCourses.stream().map(tc -> tc.getTeacher().getId()).collect(Collectors.toList());
        List<User> teachersFound = service.getUsersByIds(teachersIds);
        ResponseEntity<List<User>> response;
        if (teachersFound.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(teachersFound);
        }
        return response;
    }
    @GetMapping("/getAllTeachers")
    public ResponseEntity<List<User>> getAllTeachers() {
        List<User> users = service.getAllTeachers();
        ResponseEntity<List<User>> response;
        if (users.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(users);
        }
        return response;
    }

    @GetMapping("/teachers/{teacherId}/reviews")
    public ResponseEntity<List<Review>> getTeacherReviews(@PathVariable Long teacherId) {
        List<Review> reviews = service.getTeacherReviews(teacherId);
        ResponseEntity<List<Review>> response;
        if (reviews.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(reviews);
        }
        return response;
    }

    @GetMapping("/teachers/{teacherId}/courses")
    public ResponseEntity<List<Course>> getCoursesByTeacher(@PathVariable Long teacherId) {
        log.info("Getting courses for teacher with id: {}", teacherId);
        List<Course> courses = service.getCoursesByTeacher(teacherId);
        ResponseEntity<List<Course>> response;
        if (courses.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(courses);
        }
        return response;
    }

    @GetMapping("/teachers/{teacherId}/classes")
    public ResponseEntity<List<Class>> getClassesByTeacher(@PathVariable Long teacherId) {
        log.info("Getting classes for teacher with id: {}", teacherId);
        List<Class> classes = service.getClassesByTeacher(teacherId);
        ResponseEntity<List<Class>> response;
        if (classes.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(classes);
        }
        return response;
    }

    @GetMapping("/teachers/{teacherId}/classes/available")
    public ResponseEntity<List<Class>> getAvailableClassesByTeacher(@PathVariable Long teacherId) {
        log.info("Getting classes for teacher with id: {}", teacherId);
        List<Class> classes = service.getClassesByTeacher(teacherId);
        List<Class> availableClasses = classes.stream().filter(Class::isAvailable).collect(Collectors.toList());
        ResponseEntity<List<Class>> response;
        if (availableClasses.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(availableClasses);
        }
        return response;
    }

    @GetMapping("/students/{studentId}/classes")
    public ResponseEntity<List<Class>> getClassesByStudent(@PathVariable Long studentId) {
        log.info("Getting classes for student with id: {}", studentId);
        List<Class> classes = service.getClassesByStudent(studentId);
        ResponseEntity<List<Class>> response;
        if (classes.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(classes);
        }
        return response;
    }


}
