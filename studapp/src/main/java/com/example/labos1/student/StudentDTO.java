package com.example.labos1.student;

import lombok.Data;

@Data
public class StudentDTO {

    private String JMBAG;
    private int numberOfECTS;

    private String firstName, lastName;

    private boolean tuitionShouldBePaid;



    public StudentDTO(String JMBAG, int numberOfECTS, String firstName, String lastName, boolean tuitionShouldBePaid) {
        this.JMBAG = JMBAG;
        this.numberOfECTS = numberOfECTS;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tuitionShouldBePaid = tuitionShouldBePaid;
    }


}
