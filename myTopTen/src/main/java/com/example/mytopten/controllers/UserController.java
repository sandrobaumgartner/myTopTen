package com.example.mytopten.controllers;

import com.example.mytopten.jparepositories.UserRepository;
import com.example.mytopten.models.User;
import com.example.mytopten.models.UserModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "${myTopTen.app.cors}")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserModel getUserByUsername(@RequestParam String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("GetUserByUsername error"));
        return new UserModel(user.getId(), user.getUsername());
    }

    @GetMapping("/byPartOfUsername")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserModel> getUsersByPartOfUsername(@RequestParam String partOfUsername) {
        if(partOfUsername.equals("")) {
            return new ArrayList<>();
        }
        Pageable pageable = PageRequest.of(0, 8);
        List<User> users = userRepository.getUsersByPartOfUsername(partOfUsername, pageable)
                .orElseThrow(() -> new RuntimeException("No user found with containing string!"));
        return users.stream()
                .map(user -> new UserModel(user.getId(), user.getUsername())).collect(Collectors.toList());
    }
}
