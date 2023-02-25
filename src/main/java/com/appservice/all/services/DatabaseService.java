package com.appservice.all.services;

import com.appservice.all.Entities.User;
import com.appservice.all.Postgres.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DatabaseService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveUser(String email, String name, Integer degree, Integer departmentId, Integer year,
                         Boolean isTeacher, Double price, String privateInfo) {
        User user = User.builder()
                .email(email)
                .name(name)
                .degree(degree)
                .departmentId(departmentId)
                .year(year)
                .isTeacher(isTeacher)
                .price(price)
                .privateInfo(privateInfo)
                .build();

        userRepository.save(user);
    }


}
