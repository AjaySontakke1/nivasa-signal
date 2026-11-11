package com.nivasasignal.controller;

import com.nivasasignal.dto.LoginUserRequest;
import com.nivasasignal.dto.LoginUserResponse;
import com.nivasasignal.dto.RegisterUserRequest;
import com.nivasasignal.dto.RegisterUserResponse;
import com.nivasasignal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterUserResponse register(
            @Valid @RequestBody RegisterUserRequest request
    ) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginUserResponse login(
            @Valid @RequestBody LoginUserRequest request
    ) {
        return userService.login(request);
    }
}
