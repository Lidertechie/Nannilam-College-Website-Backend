package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.LoginRequest;
import com.Lider.college_website.dto.request.RegisterRequest;
import com.Lider.college_website.dto.response.AuthResponse;
import com.Lider.college_website.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}