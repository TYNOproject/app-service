package com.appservice.all.Postgres;

import com.appservice.all.Entities.Department;
import com.appservice.all.Entities.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Integer> {
    List<TeacherCourse> findAllByTeacherId(Integer teacherId);

    List<TeacherCourse> findAllByCourseId(Integer courseId);
}
