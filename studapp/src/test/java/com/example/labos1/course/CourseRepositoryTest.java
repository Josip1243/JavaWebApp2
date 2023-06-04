package com.example.labos1.course;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findAll() {
        List<Course> courses = courseRepository.findAll();
        assertNotNull(courses);
    }

    @Test
    void findCoursesByStudentJmbag() {
        String JMBAG = "1234567890";

        List<Course> courses = courseRepository.findCoursesByStudents_Jmbag(JMBAG);
        assertNotNull(courses);
    }
}