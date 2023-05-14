package com.example.labos1.course;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {

    String name;
    Integer numberOfECTS;

}
