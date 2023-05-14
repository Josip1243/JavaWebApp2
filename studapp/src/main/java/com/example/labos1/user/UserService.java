package com.example.labos1.user;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> findByUsername(String username);

}
