package com.Lider.college_website.service;

import com.Lider.college_website.dto.request.ChangePasswordRequest;
import com.Lider.college_website.dto.request.ForgotPasswordRequest;
import com.Lider.college_website.dto.request.ResetPasswordRequest;
import com.Lider.college_website.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
    void changePassword(Long userId, ChangePasswordRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}