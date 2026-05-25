package com.taskflow.api.service;

import com.taskflow.api.dto.LoginRequestDTO;
import com.taskflow.api.dto.LoginResponseDTO;
import com.taskflow.api.model.User;
import com.taskflow.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponseDTO(token, user.getName(), user.getEmail());
    }
}