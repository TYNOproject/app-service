package com.appservice.all.controllers;

import com.appservice.all.Entities.User;
import com.appservice.all.Entities.UserRequest;
import com.appservice.all.services.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
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
}
