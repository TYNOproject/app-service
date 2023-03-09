package com.appservice.all.Postgres;

import com.appservice.all.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByDepartmentId(Long departmentId);

    Course findCourseByCourseName(String courseName);

    List<Course> findAllByCourseNameContainingIgnoreCase(String courseName);

    List<Course> findAllById(Iterable<Long> courseIds);
}
