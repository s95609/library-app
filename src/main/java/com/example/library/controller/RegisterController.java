package com.example.library.controller;

import com.example.library.dto.UserRequest;
import com.example.library.model.User;
import com.example.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegisterController {
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody UserRequest loginRequest){
        return userService.authenticateUser(loginRequest);
    }
}
