package com.taskflow.api.controller;

import com.taskflow.api.dto.UserResponseDTO;
import com.taskflow.api.model.User;
import com.taskflow.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody User user) {
        User created = userService.create(user);
        
        UserResponseDTO response = new UserResponseDTO();
        response.setId(created.getId());
        response.setName(created.getName());
        response.setEmail(created.getEmail());
        response.setCreatedAt(created.getCreatedAt());
        response.setUpdatedAt(created.getUpdatedAt());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}