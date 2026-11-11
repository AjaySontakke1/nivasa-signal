package com.nivasasignal.service;

import com.nivasasignal.dto.LoginUserRequest;
import com.nivasasignal.dto.LoginUserResponse;
import com.nivasasignal.dto.RegisterUserRequest;
import com.nivasasignal.dto.RegisterUserResponse;
import com.nivasasignal.entity.User;
import com.nivasasignal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterUserResponse register(RegisterUserRequest request) {

        String email = request.email().trim().toLowerCase();

        boolean emailAlreadyExists = userRepository.existsByEmail(email);

        if (emailAlreadyExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );
        }

        User user = new User();

        user.setFullName(request.fullName().trim());
        user.setEmail(email);
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setAdmin(false);
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

        return new RegisterUserResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.isEmailVerified(),
                savedUser.getCreatedAt()
        );
    }

    public LoginUserResponse login(LoginUserRequest request) {

        String email = request.email().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                ));

        boolean passwordMatches = passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginUserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.isAdmin(),
                token
        );
    }
}
