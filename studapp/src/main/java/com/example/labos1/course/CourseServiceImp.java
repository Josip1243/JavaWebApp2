package com.example.labos1.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImp implements CourseService{

    @Autowired
    CourseRepository courseRepository;

    public CourseServiceImp(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(this::mapCourseToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> findCoursesByStudents_Jmbag(String jmbag) {
        return courseRepository.findCoursesByStudents_Jmbag(jmbag).stream()
                .map(this::mapCourseToDTO)
                .collect(Collectors.toList());
    }


    private CourseDTO mapCourseToDTO(Course course){
        return new CourseDTO(course.getNaziv(), course.getEcts());
    }

}
