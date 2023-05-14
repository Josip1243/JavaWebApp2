package com.example.labos1.course;

import java.util.List;

public interface CourseService {

    List<CourseDTO> findAll();

    List<CourseDTO> findCoursesByStudents_Jmbag(String jmbag);

}
