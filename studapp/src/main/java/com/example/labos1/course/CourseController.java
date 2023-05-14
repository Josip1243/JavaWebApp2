package com.example.labos1.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    @Autowired
    CourseServiceImp courseService;

    @GetMapping("/course")
    List<CourseDTO> getAllCourses(){
        return courseService.findAll();
    }

    @GetMapping(path = "/course", params = "jmbag")
    public List<CourseDTO> getAllCoursesByStudentJmbag(@RequestParam String jmbag){
        System.out.println(courseService.findCoursesByStudents_Jmbag(jmbag));
        return courseService.findCoursesByStudents_Jmbag(jmbag);
    }

}
