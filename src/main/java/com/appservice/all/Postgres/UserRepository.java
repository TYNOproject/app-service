package com.appservice.all.Postgres;
import com.appservice.all.Entities.Department;
import com.appservice.all.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE is_teacher = true", nativeQuery = true)
    List<User> findAllTeachers();
    User findByEmail(String email);

}


