package com.appservice.all.controllers;

import com.appservice.all.Entities.Course;
import com.appservice.all.Entities.GetCoursesByDepartmentRequest;
import com.appservice.all.Entities.User;
import com.appservice.all.Entities.UserRequest;
import com.appservice.all.services.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/db")
public class MainController {

    @Autowired
    DatabaseService service;


    @GetMapping
    public String helloWorld(){
        return "Hello!";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
        String email = userRequest.getEmail();
        String name = userRequest.getName();
        Integer degree = userRequest.getDegree();
        Integer departmentId = userRequest.getDepartmentId();
        Integer year = userRequest.getYear();
        boolean isTeacher = userRequest.isTeacher();
        Double price = userRequest.getPrice();
        String privateInfo = userRequest.getPrivateInfo();
        service.saveUser(email, name, degree, departmentId, year, isTeacher, price, privateInfo);
        return ResponseEntity.ok("User added successfully");
    }

    @PostMapping("/getCoursesByDepartment")
    public List<Course> getCoursesByDepartment(@RequestBody GetCoursesByDepartmentRequest userRequest) {
        log.info("got a request with id:{}", userRequest.getDepartmentId());
        return service.getCoursesByDepartmentName(userRequest.getDepartmentId());
    }


}
