package com.appservice.all.controllers;

import com.appservice.all.Entities.User;
import com.appservice.all.Postgres.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/test")
public class MainController {

    @Autowired
    MainRepository mainRepository;

    @GetMapping
    public String helloWorld(){
        return "Hello!";
    }

    @GetMapping("/{name}")
    public String heyYou(@PathVariable(value = "name") String name){
        List<User> foundUsers = mainRepository.findAll();
        return "Hey " + name + ", Here is a list of all users in DB:" + "\n" + foundUsers ;
    }
}
