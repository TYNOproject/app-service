package com.appservice.all.Postgres;

import com.appservice.all.Entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findAllByTeacherId(Long teacherId);
}
