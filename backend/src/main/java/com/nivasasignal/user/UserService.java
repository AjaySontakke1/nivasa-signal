package com.nivasasignal.user;

import com.nivasasignal.user.dto.RegisterUserRequest;
import com.nivasasignal.user.dto.RegisterUserResponse;
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

    public RegisterUserResponse register(RegisterUserRequest request) {

        String email = request.email().trim().toLowerCase();

        boolean emailAlreadyExists = userRepository.existsByEmail(email);

        if (emailAlreadyExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );
        }

        if (request.role() != UserRole.BUYER
                && request.role() != UserRole.OWNER) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only BUYER or OWNER can register"
            );
        }

        User user = new User();

        user.setFullName(request.fullName().trim());
        user.setEmail(email);
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setRole(request.role());
        user.setEmailVerified(false);

        User savedUser = userRepository.save(user);

        return new RegisterUserResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.isEmailVerified(),
                savedUser.getCreatedAt()
        );
    }
}
