package com.example.labos1.student;

import java.util.Optional;
import java.util.Set;

public interface StudentService {

    Set<StudentDTO> findAll();
    Optional<StudentDTO> findStudentByJMBAG(String JMBAG);

    Optional<StudentDTO> save(StudentCommand command);

    boolean deleteByJMBAG(String jmbag);

    Optional<StudentDTO> update(String JMBAG, StudentCommand updatedStudentCommand);

}
