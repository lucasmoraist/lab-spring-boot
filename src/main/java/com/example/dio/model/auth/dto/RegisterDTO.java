package com.example.dio.model.auth.dto;

import com.example.dio.model.user.UserRole;

public record RegisterDTO(String email, String password, UserRole role) { }
