package com.example.labos1.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Repository
public class JdbcStudentRepository implements StudentRepository{


    private static final String SELECT_ALL =
            "SELECT id, jmbag, first_name, last_name, ects_points, date_of_birth FROM student";

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert inserter;


    public JdbcStudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.inserter = new SimpleJdbcInsert(jdbc)
                .withTableName("student")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Set<Student> findAll() {
        return Set.copyOf(jdbc.query(SELECT_ALL, this::mapRowToStudent));
    }


    @Override
    public Optional<Student> findStudentByJMBAG(String JMBAG) {
        try{
            return Optional.ofNullable(
                    jdbc.queryForObject(SELECT_ALL + " WHERE jmbag = ?", this::mapRowToStudent, JMBAG)
            );
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }



    @Override
    public Optional<Student> save(Student student) {

        System.out.println(student);

        try {

            student.setId( saveStudentDetails(student));
            System.out.println(student);


            return Optional.of(student);

        } catch (DuplicateKeyException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteByJMBAG(String JMBAG) {

        Optional<Student> student = findStudentByJMBAG(JMBAG);


        if(student.isPresent())
            jdbc.update("DELETE FROM student_course WHERE student_id = ?", student.get().getId());

        return jdbc.update("DELETE FROM student WHERE jmbag = ?", JMBAG) == 1;
    }


    private Student mapRowToStudent(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("jmbag"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getInt("ects_points"),
                rs.getLong("id")
        );
    }

    private long saveStudentDetails(Student student) {

        Map<String, Object> values = new HashMap<>();

        values.put("first_name", student.getFirstName());
        values.put("last_name", student.getLastName());
        values.put("jmbag", student.getJmbag());
        values.put("date_of_birth", student.getDateOfBirth());
        values.put("ects_points", student.getNumberOfECTS());

        System.out.println(values);

        return (long) inserter.executeAndReturnKey(values).longValue();
    }

    @Override
    public Optional<Student> update(String JMBAG, Student updatedStudent) {
        int executed = jdbc.update("UPDATE student set " +
                        "first_name = ?, " +
                        "last_name = ?, " +
                        "ects_points = ?, " +
                        "date_of_birth = ? " +
                        "WHERE jmbag = ?",
                updatedStudent.getFirstName(),
                updatedStudent.getLastName(),
                updatedStudent.getNumberOfECTS(),
                updatedStudent.getDateOfBirth(),
                JMBAG
        );

        if(executed > 0){
            return Optional.of(updatedStudent);
        } else {
            return Optional.empty();
        }
    }

}
