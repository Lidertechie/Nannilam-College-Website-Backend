package com.Lider.college_website.service.impl;

import com.Lider.college_website.dto.request.ChangePasswordRequest;
import com.Lider.college_website.dto.request.ForgotPasswordRequest;
import com.Lider.college_website.dto.request.ResetPasswordRequest;
import com.Lider.college_website.dto.response.UserResponse;
import com.Lider.college_website.entity.PasswordResetToken;
import com.Lider.college_website.entity.User;
import com.Lider.college_website.exception.ResourceNotFoundException ;
import com.Lider.college_website.repository.PasswordResetTokenRepository;
import com.Lider.college_website.repository.UserRepository;
import com.Lider.college_website.service.EmailService;
import com.Lider.college_website.service.UserService;
import com.Lider.college_website.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("User", "id", id));
        return UserMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> ResourceNotFoundException.forEntity("User", "id", userId));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResourceNotFoundException ("Old password is incorrect");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ResourceNotFoundException ("New password and confirm password do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> ResourceNotFoundException.forEntity("User", "email", request.getEmail()));

        tokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                null,
                token,
                user.getId(),
                LocalDateTime.now().plusMinutes(30)
        );
        tokenRepository.save(resetToken);

        String resetLink = "https://vbk0n794-3000.inc1.devtunnels.ms/Resetpassword?token=" + token;

        emailService.sendPasswordResetEmail(user.getEmail(), user.getFullName(), resetLink);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new ResourceNotFoundException ("Invalid or expired token"));

        if (resetToken.isExpired()) {
            tokenRepository.delete(resetToken);
            throw new ResourceNotFoundException ("Token has expired, please request a new one");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ResourceNotFoundException ("New password and confirm password do not match");
        }

        User user = userRepository.findById(resetToken.getUserId())
                .orElseThrow(() -> ResourceNotFoundException.forEntity("User", "id", resetToken.getUserId()));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        tokenRepository.delete(resetToken);
    }
}