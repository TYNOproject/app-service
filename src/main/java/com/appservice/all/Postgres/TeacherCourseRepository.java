package com.appservice.all.Postgres;

import com.appservice.all.Entities.Department;
import com.appservice.all.Entities.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
    List<TeacherCourse> findAllByTeacherId(Long teacherId);
    List<TeacherCourse> findAllByCourseId(Long courseId);
}
