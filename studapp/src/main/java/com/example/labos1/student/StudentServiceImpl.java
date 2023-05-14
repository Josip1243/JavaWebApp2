package com.example.labos1.student;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    private static final int YEARS_AFTER_WHICH_TUITION_SHOULD_BE_PAYED = 26;
    //private final MockStudentRepository studentRepository;
    private final JdbcStudentRepository studentRepository;

    public StudentServiceImpl(JdbcStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    //get
    @Override
    public Set<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(this::mapStudentToDTO).collect(Collectors.toSet());
    }
    @Override
    public Optional<StudentDTO> findStudentByJMBAG(final String JMBAG) {
        return studentRepository.findStudentByJMBAG(JMBAG).map(this::mapStudentToDTO);
    }

    //post
    @Override
    public Optional<StudentDTO> save(StudentCommand command) {
        System.out.println(command);
        return studentRepository.save(convertStudentCommandToStudent(command)).map(this::mapStudentToDTO);
    }

    @Override
    public boolean deleteByJMBAG(String jmbag) {
        return studentRepository.deleteByJMBAG(jmbag);
    }


    private StudentDTO mapStudentToDTO(final Student student){
        return new StudentDTO(student.getJmbag(), student.getNumberOfECTS(), student.getFirstName(), student.getLastName(), shouldTuitionBePayed(student.getDateOfBirth()));
    }
    private boolean shouldTuitionBePayed(LocalDate dateOfBirth){
        return dateOfBirth.plusYears(YEARS_AFTER_WHICH_TUITION_SHOULD_BE_PAYED).isBefore(LocalDate.now());
    }

    private Student convertStudentCommandToStudent(StudentCommand command){
        return new Student( command.getFirstName(), command.getLastName(), command.getJmbag(), command.getDateOfBirth(), command.getNumberOfECTS());
    }


    @Override
    public Optional<StudentDTO> update(final String JMBAG, final StudentCommand updatedStudentCommand) {
        return studentRepository.update(JMBAG, convertStudentCommandToStudent(updatedStudentCommand)).map(this::mapStudentToDTO);
    }


}
