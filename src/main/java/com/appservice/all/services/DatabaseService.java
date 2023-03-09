package com.appservice.all.services;

import com.appservice.all.Entities.Course;
import com.appservice.all.Entities.Review;
import com.appservice.all.Entities.TeacherCourse;
import com.appservice.all.Entities.User;
import com.appservice.all.Postgres.ClassRepository;
import com.appservice.all.Postgres.CourseRepository;
import com.appservice.all.Postgres.TeacherCourseRepository;
import com.appservice.all.Postgres.UserRepository;
import com.appservice.all.Entities.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Transactional
    public void saveUser(String email,String password, String name, Integer degree,Integer faculty, Integer departmentId, Integer year,
                         Boolean isTeacher, Double price, String privateInfo) {
        User user = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .degree(degree)
                .facultyId(faculty)
                .departmentId(departmentId)
                .year(year)
                .isTeacher(isTeacher)
                .price(price)
                .privateInfo(privateInfo)
                .build();

        userRepository.save(user);
    }

    public List<Course> getCoursesByDepartmentName(Integer departmentId) {
        return courseRepository.findAllByDepartmentId(departmentId);
    }


    public Course getCourseByName(String courseName) {
        return courseRepository.findCourseByCourseName(courseName);
    }

    public List<TeacherCourse> getTeacherCoursesByCourse(Course course) {
        return teacherCourseRepository.findAllByCourseId(course.getId());
    }

    public List<User> getUsersByIds(List<Integer> userIds) {
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

    public List<Review> getTeacherReviews(Integer teacherId) {
        List<Review> reviews = new ArrayList<>();
        List<Class> classes = classRepository.findAllByTeacherId(teacherId);
        for (Class cls : classes) {
            Review review = Review.builder()
                    .textReview(cls.getTextReview())
                    .starsReview(cls.getStarsReview())
                    .build();
            reviews.add(review);
        }
        return reviews;
    }

    public List<Course> getCoursesByTeacher(Integer teacherId) {
        List<Course> courses = new ArrayList<>();
        List<TeacherCourse> teacherCourses = teacherCourseRepository.findAllByTeacherId(teacherId);
        List<Integer> courseIds = teacherCourses.stream().map(TeacherCourse::getCourseId).collect(Collectors.toList());
        return courseRepository.findAllById(courseIds);
    }

    public List<Class> getClassesByTeacher(Integer teacherId) {
        return classRepository.findAllByTeacherId(teacherId);
    }

    public void saveClass(Integer courseId, Integer teacherId, LocalDateTime startTime, LocalDateTime endTime, String status) {
        Class cls = Class.builder()
                .courseId(courseId)
                .teacherId(teacherId)
                .startTime(startTime)
                .endTime(endTime)
                .status(status)
                .build();
        classRepository.save(cls);
    }
    @Transactional
    public String bookClass(Integer classId, Integer studentId) {
        Optional<Class> optionalClass = classRepository.findById(classId);
        if (optionalClass.isPresent()) {
            Class cls = optionalClass.get();

            if (cls.getStatus().equals("available")) {
                cls.setStudentId(studentId);
                cls.setStatus("booked");
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
    public boolean updatePersonalDetails(Integer studentId, String privateInfo) {
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
    public boolean addReview(Integer classId, String textReview, Integer starsReview) {
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
    public boolean setStudentAsTeacher(Integer studentId) {
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

    public List<User> getTeachersForCourse(Integer courseId) {
        List<TeacherCourse> teacherCourses = teacherCourseRepository.findAllByCourseId(courseId);
        List<Integer> teacherIds = teacherCourses.stream().map(TeacherCourse::getTeacherId).collect(Collectors.toList());
        return userRepository.findAllById(teacherIds);
    }

    public List<Course> searchCourses(String courseName, Integer departmentId, Integer year) {
        List<Course> courses = courseRepository.findAll();

        if(courseName != null) {
            courses = courses.stream().filter(c -> c.getCourseName().equals(courseName)).collect(Collectors.toList());
        }
        if(departmentId != null) {
            courses = courses.stream().filter(c -> c.getDepartmentId().equals(departmentId)).collect(Collectors.toList());
        }
        if(year != null) {
            courses = courses.stream().filter(c -> c.getYear().equals(year)).collect(Collectors.toList());
        }
        return courses;
    }
}
