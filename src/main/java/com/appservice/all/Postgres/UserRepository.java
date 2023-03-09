package com.appservice.all.Postgres;
import com.appservice.all.Entities.Course;
import com.appservice.all.Entities.Department;
import com.appservice.all.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIsTeacherTrue();
    List<User> findAll();
    List<User> findAllById(Iterable<Long> ids);

    User findByEmail(String email);

    List<User> findAllByIsTeacherTrueAndNameContainingIgnoreCase(String searchString);
}


