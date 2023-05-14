package com.example.labos1.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String first_name;
    private String last_name;
    private Set<String> authorities;


    public UserDTO(Long id, String username, String first_name, String last_name, Set<String> authorities) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.authorities = authorities;
    }
}
