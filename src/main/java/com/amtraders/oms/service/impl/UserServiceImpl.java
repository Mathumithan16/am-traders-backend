package com.amtraders.oms.service.impl;

import com.amtraders.oms.dto.request.ForgotPasswordRequest;
import com.amtraders.oms.dto.request.LoginRequest;
import com.amtraders.oms.dto.request.RegisterRequest;
import com.amtraders.oms.dto.request.ResetPasswordRequest;
import com.amtraders.oms.dto.response.AuthResponse;
import com.amtraders.oms.entity.PasswordResetToken;
import com.amtraders.oms.entity.User;
import com.amtraders.oms.enums.Role;
import com.amtraders.oms.enums.UserStatus;
import com.amtraders.oms.exception.BadRequestException;
import com.amtraders.oms.exception.ResourceNotFoundException;
import com.amtraders.oms.repository.PasswordResetTokenRepository;
import com.amtraders.oms.repository.UserRepository;
import com.amtraders.oms.service.EmailService;
import com.amtraders.oms.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        return new AuthResponse(user.getUsername(), user.getEmail(), user.getRole().name());
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .or(() -> userRepository.findByEmail(request.getUsername()))
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        return new AuthResponse(user.getUsername(), user.getEmail(), user.getRole().name());
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No user found with email: " + request.getEmail()));

        // Delete existing token if present
        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        tokenRepository.save(resetToken);

        // Send email
        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token; // Default placeholder URL
        emailService.sendEmail(user.getEmail(), "Password Reset Request", 
                "To reset your password, click the link below:\n" + resetUrl);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new BadRequestException("Invalid or missing token"));

        if (resetToken.getExpiryDate().before(new Date())) {
            tokenRepository.delete(resetToken);
            throw new BadRequestException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        
        tokenRepository.delete(resetToken);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
