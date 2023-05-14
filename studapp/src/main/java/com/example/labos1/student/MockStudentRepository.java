package com.example.labos1.student;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public class MockStudentRepository implements StudentRepository{


    private final Set<Student> MOCKED_STUDENTS = new HashSet<>(Arrays.asList(
            new Student("Ivo", "Ivić", "0256423323", LocalDate.now().minusYears(27), 120, 1L),
            new Student("Lucija", "Lucić", "0256423324", LocalDate.now().minusYears(25), 98, 2L)
    ));


    @Override
    public Set<Student> findAll() {
        return MOCKED_STUDENTS;
    }

    @Override
    public Optional<Student> findStudentByJMBAG(String JMBAG) {
        return MOCKED_STUDENTS.stream().filter(it -> Objects.equals(it.getJmbag(), JMBAG)).findAny();
    }

    @Override
    public Optional<Student> save(Student student) {
        System.out.println(student);
        if(!MOCKED_STUDENTS.contains(student)){
            MOCKED_STUDENTS.add(student);
            return Optional.of(student);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteByJMBAG(String jmbag) {
        int lastLength = MOCKED_STUDENTS.size();
        MOCKED_STUDENTS.removeIf(student -> student.getJmbag().equals(jmbag));

        return lastLength != MOCKED_STUDENTS.size();
    }

    @Override
    public Optional<Student> update(String JMBAG, Student updatedStudent) {
        return Optional.empty();
    }


}
