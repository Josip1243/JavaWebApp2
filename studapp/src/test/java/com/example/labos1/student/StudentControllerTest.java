package com.example.labos1.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers. status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers. view;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final StudentCommand STUDENT = new StudentCommand("Test", "Test", "1111111111", LocalDate.now().minusYears(25),
            180);
    @Test
    void getAllStudents() throws Exception {
        this.mockMvc.perform(
                        get("/student")
                                .with(user("admin")
                                        .password("test")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getStudentByJMBAG() throws Exception {
        String TEST_JMBAG = "0246053232";
        String TEST_FIRST_NAME = "Ivo";
        String TEST_LAST_NAME = "Ivic";

        this.mockMvc.perform(
                        get("/student/" + TEST_JMBAG)
                                .with(user("admin")
                                        .password("test")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jmbag").value(TEST_JMBAG))
                .andExpect(jsonPath("$.firstName").value(TEST_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(TEST_LAST_NAME));
    }

    @DirtiesContext
    @Test
    void save() throws Exception {
        String TEST_JMBAG = "0246011232";
        String TEST_FIRST_NAME = "Pedro";
        String TEST_LAST_NAME = "Pedrić";
        LocalDate TEST_DATE_OF_BIRTH = LocalDate.now().minusYears(21);
        Integer TEST_NUMBER_OF_ECTS = 120;

        StudentCommand studentCommand = new StudentCommand(
                TEST_FIRST_NAME,
                TEST_LAST_NAME,
                TEST_JMBAG,
                TEST_DATE_OF_BIRTH,
                TEST_NUMBER_OF_ECTS
        );

        this.mockMvc.perform(
                        post("/student")
                                .with(user("admin")
                                        .password("test")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(studentCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jmbag").value(TEST_JMBAG))
                .andExpect(jsonPath("$.firstName").value(TEST_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(TEST_LAST_NAME));
    }

    @Test
    void updateStudent() throws Exception {

        objectMapper.registerModule(new JavaTimeModule());

        String JMBAG = "0246053233";

        this.mockMvc.perform(
                    put("/student/{JMBAG}", JMBAG)
                    .with(user("admin")
                            .password("test")
                            .roles("ADMIN")
                    )
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(STUDENT))
                    .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.jmbag").value(STUDENT.getJmbag()))
                .andExpect(jsonPath("$.firstName").value(STUDENT.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(STUDENT.getLastName()));
    }

    @Test
    @DirtiesContext
    void deleteStudent() throws Exception {
        String JMBAG = "0246053232";

        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/student/{JMBAG}",JMBAG)
                                .with(user("admin")
                                        .password("test")
                                        .roles("ADMIN")
                                )
                                .with(csrf())
                )
                .andExpect(status().isOk());

    }
}