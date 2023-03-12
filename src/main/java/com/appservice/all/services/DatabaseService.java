package com.appservice.all.services;

import com.appservice.all.Entities.Course;
import com.appservice.all.Entities.Review;
import com.appservice.all.Entities.TeacherCourse;
import com.appservice.all.Entities.User;
import com.appservice.all.Postgres.*;
import com.appservice.all.Entities.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatabaseService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TeacherCourseRepository teacherCourseRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public void saveUser(String email,String password, String name, Integer degree,Long faculty, Long departmentId, Integer year,
                         Boolean isTeacher) {
        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .degree(degree)
                .faculty(facultyRepository.findById(faculty).orElse(null))
                .department(departmentRepository.findById(departmentId).orElse(null))
                .year(year)
                .price(0.0)
                .isTeacher(isTeacher)
                .privateInfo("")
                .build();

        userRepository.save(user);
    }

    public List<Course> getCoursesByDepartmentName(Long departmentId) {
        return courseRepository.findAllByDepartmentId(departmentId);
    }


    public Course getCourseByName(String courseName) {
        return courseRepository.findCourseByCourseName(courseName);
    }

    public List<TeacherCourse> getTeacherCoursesByCourse(Course course) {
        return teacherCourseRepository.findAllByCourseId(course.getId());
    }

    public List<User> getUsersByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }
    public List<User> getTeachersContainingString(String searchString) {
        return userRepository.findAllByIsTeacherTrueAndNameContainingIgnoreCase(searchString);
    }

    public List<Course> getCoursesByCourseName(String courseName) {
        return courseRepository.findAllByCourseNameContainingIgnoreCase(courseName);
    }

    public List<User> getAllTeachers() {
        return userRepository.findAllByIsTeacherTrue();
    }

    public List<Review> getTeacherReviews(Long teacherId) {
        List<Review> reviews = new ArrayList<>();
        List<Class> classes = classRepository.findAllByTeacherId(teacherId);
        for (Class cls : classes) {
            if(cls.getTextReview() != null && cls.getStarsReview() != null){
                Review review = Review.builder()
                        .textReview(cls.getTextReview())
                        .starsReview(cls.getStarsReview())
                        .studentId(cls.getStudent().getId())
                        .teacherId(cls.getTeacher().getId())
                        .studentName(cls.getStudent().getName())
                        .teacherName(cls.getTeacher().getName())
                        .build();
                reviews.add(review);
            }
        }
        return reviews;
    }

    public List<Course> getCoursesByTeacher(Long teacherId) {
        List<Course> courses = new ArrayList<>();
        List<TeacherCourse> teacherCourses = teacherCourseRepository.findAllByTeacherId(teacherId);
        List<Long> courseIds = teacherCourses.stream().map(tc -> tc.getCourse().getId()).collect(Collectors.toList());
        return courseRepository.findAllById(courseIds);
    }

    public List<Class> getClassesByTeacher(Long teacherId) {
        return classRepository.findAllByTeacherId(teacherId);
    }

    public void saveClass(Long courseId, Long teacherId, Date date, LocalTime startTime, LocalTime endTime, String status) {
        Class cls = Class.builder()
                .course(courseRepository.findById(courseId).get())
                .teacher(userRepository.findById(teacherId).get())
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .build();
        classRepository.save(cls);
    }
    @Transactional
    public String bookClass(Long classId, Long studentId) {
        Optional<Class> optionalClass = classRepository.findById(classId);
        if (optionalClass.isPresent()) {
            Class cls = optionalClass.get();

            if (cls.getStatus().equals("available")) {
                cls.setStudent(userRepository.findById(studentId).get());
                cls.setStatus("pending");
                classRepository.save(cls);
                return "Successfully booked class";
            } else {
                return "Class is not available";
            }
        } else {
            return "Class not found";
        }
    }
    @Transactional
    public boolean updatePersonalDetails(Long studentId, String privateInfo) {
        Optional<User> optionalUser = userRepository.findById(studentId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPrivateInfo(privateInfo);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
    @Transactional
    public boolean addReview(Long classId, String textReview, Integer starsReview) {
        Optional<Class> optionalClass = classRepository.findById(classId);

        if (optionalClass.isPresent()) {
            Class cls = optionalClass.get();
            cls.setStarsReview(starsReview);
            cls.setTextReview(textReview);
            classRepository.save(cls);
            return true;
        } else {
            return false;
        }
    }
    @Transactional
    public boolean setStudentAsTeacher(Long studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setIsTeacher(true);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public ResponseEntity<User> signIn(String email, String password) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public List<User> getTeachersForCourse(Long courseId) {
        List<TeacherCourse> teacherCourses = teacherCourseRepository.findAllByCourseId(courseId);
        List<Long> teacherIds = teacherCourses.stream().map(tc -> tc.getTeacher().getId()).collect(Collectors.toList());
        return userRepository.findAllById(teacherIds);
    }

    public List<Course> searchCourses(String courseName, Long departmentId, Integer year) {
        List<Course> courses;
        if(courseName != null) {
            courses = courseRepository.findAllByCourseNameContainingIgnoreCase(courseName);
        } else {
             courses = courseRepository.findAll();
        }
        if(departmentId != null) {
            courses = courses.stream().filter(c -> c.getDepartment().getId().equals(departmentId)).collect(Collectors.toList());
        }
        if(year != null) {
            courses = courses.stream().filter(c -> c.getYear().equals(year)).collect(Collectors.toList());
        }
        return courses;
    }

    public List<Class> getClassesByStudent(Long studentId) {
        return classRepository.findAllByStudentId(studentId);
    }
}
