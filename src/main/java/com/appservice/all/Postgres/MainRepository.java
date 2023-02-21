package com.appservice.all.Postgres;


import com.appservice.all.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainRepository extends JpaRepository<User, Integer> {
}
