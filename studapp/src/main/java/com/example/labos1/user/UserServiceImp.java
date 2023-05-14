package com.example.labos1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findOneByUsername(username)
                .map(this::mapUserToDTO);
    }

    private UserDTO mapUserToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getFirst_name(), user.getLast_name(),
                user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
    }


}
