package com.appservice.all.controllers;

import com.appservice.all.Entities.*;
import com.appservice.all.Entities.Class;
import com.appservice.all.services.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("api/db")
public class MainController {

    public static final String AVAILABLE = "available";
    @Autowired
    DatabaseService service;


    @GetMapping
    public String helloWorld() {
        return "Hello!";
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> addUser(@RequestBody AddNewUserRequest addNewUserRequest) {
        String email = addNewUserRequest.getEmail();
        String name = addNewUserRequest.getName();
        Integer degree = addNewUserRequest.getDegree();
        Integer faculty = addNewUserRequest.getFacultyId();
        Integer departmentId = addNewUserRequest.getDepartmentId();
        Integer year = addNewUserRequest.getYear();
        boolean isTeacher = addNewUserRequest.isTeacher();
        Double price = addNewUserRequest.getPrice();
        String privateInfo = addNewUserRequest.getPrivateInfo();
        service.saveUser(email, name, degree, faculty, departmentId, year, isTeacher, price, privateInfo);
        return ResponseEntity.ok("User added successfully");
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> addUser(@RequestBody SignInRequest signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();
        return service.signIn(email, password);

    }

    @PostMapping("/add/class")
    public ResponseEntity<?> addNewClass(@RequestBody AddNewClassRequest addNewUserRequest) {
        Integer courseId = addNewUserRequest.getCourseId();
        Integer teacherId = addNewUserRequest.getTeacherId();
        LocalDateTime startTime = addNewUserRequest.getStartTime();
        LocalDateTime endTime = addNewUserRequest.getEndTime();
        String status = AVAILABLE;
        service.saveClass(courseId, teacherId, startTime, endTime, status);

        return ResponseEntity.ok("Class added successfully");
    }

    @PostMapping("/bookClass")
    public ResponseEntity<?> bookClass(@RequestBody BookClassRequest bookClassRequest) {
        Integer classId = bookClassRequest.getClassId();
        Integer studentId = bookClassRequest.getStudentId();
        String response = service.bookClass(classId, studentId);
        if (response.equals("Class booked successfully"))
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/addReviewToClass")
    public ResponseEntity<?> bookClass(@RequestBody AddReviewToClassRequest addReviewToClassRequest) {
        Integer classId = addReviewToClassRequest.getClassId();
        String textReview = addReviewToClassRequest.getTextReview();
        Integer starsReview = addReviewToClassRequest.getStarsReview();
        boolean response = service.addReview(classId, textReview, starsReview);
        if (response)
            return ResponseEntity.ok("Review added successfully");
        else
            return ResponseEntity.badRequest().body("Request failed");
    }

    @PostMapping("/updatePersonalDetails")
    public ResponseEntity<?> bookClass(@RequestBody UpdatePersonalDetailsRequest updatePersonalDetailsRequest) {
        Integer studentId = updatePersonalDetailsRequest.getStudentId();
        String privateInfo = updatePersonalDetailsRequest.getPrivateInfo();
        boolean response = service.updatePersonalDetails(studentId, privateInfo);
        if (response)
            return ResponseEntity.ok("Successfully updated personal details");
        else
            return ResponseEntity.badRequest().body("Request failed");
    }

    @PostMapping("/setStudentAsTeacher")
    public ResponseEntity<?> bookClass(@RequestBody SetStudentAsTeacherRequest setStudentAsTeacherRequest) {
        Integer studentId = setStudentAsTeacherRequest.getStudentId();
        boolean response = service.setStudentAsTeacher(studentId);
        if (response)
            return ResponseEntity.ok("Successfully signed student as teacher");
        else
            return ResponseEntity.badRequest().body("Request failed");
    }

    @GetMapping("/{departmentId}/courses")
    public ResponseEntity<List<Course>> getCoursesByDepartment(@PathVariable Integer departmentId) {
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

    @PostMapping("/getTeachersByCourse")
    public ResponseEntity<List<User>> getTeachersByCourse(@RequestBody GetTeachersByCourseRequest getTeachersByCourseRequest) {
        Course course = service.getCourseByName(getTeachersByCourseRequest.getCourseName());
        List<TeacherCourse> teacherCourses = service.getTeacherCoursesByCourse(course);
        List<Integer> teachersIds = teacherCourses.stream().map(TeacherCourse::getTeacherId).collect(Collectors.toList());
        List<User> teachersFound = service.getUsersByIds(teachersIds);
        ResponseEntity<List<User>> response;
        if (teachersFound.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(teachersFound);
        }
        return response;
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

    @PostMapping("/getCoursesByCourseName")
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

    @GetMapping("/teacher/{teacherId}/reviews")
    public ResponseEntity<List<Review>> getTeacherReviews(@PathVariable Integer teacherId) {
        List<Review> reviews = service.getTeacherReviews(teacherId);
        ResponseEntity<List<Review>> response;
        if (reviews.isEmpty()) {
            response = ResponseEntity.notFound().build();
        } else {
            response = ResponseEntity.ok(reviews);
        }
        return response;
    }

    @GetMapping("/teacher/{teacherId}/courses")
    public ResponseEntity<List<Course>> getCoursesByTeacher(@PathVariable Integer teacherId) {
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

    @GetMapping("/teacher/{teacherId}/classes")
    public ResponseEntity<List<Class>> getClassesByTeacher(@PathVariable Integer teacherId) {
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

    @GetMapping("/teacher/{teacherId}/classes/available")
    public ResponseEntity<List<Class>> getAvailableClassesByTeacher(@PathVariable Integer teacherId) {
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


}
