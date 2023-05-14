package com.example.labos1.student;

import java.util.Optional;
import java.util.Set;

public interface StudentRepository {

    Set<Student> findAll();
    Optional<Student> findStudentByJMBAG(String JMBAG);
    Optional<Student> save(Student student);

    boolean deleteByJMBAG(String jmbag);

    Optional<Student> update(String JMBAG, Student updatedStudent);

}
