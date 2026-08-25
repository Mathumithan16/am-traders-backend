package com.amtraders.oms.service;

import com.amtraders.oms.dto.request.ForgotPasswordRequest;
import com.amtraders.oms.dto.request.LoginRequest;
import com.amtraders.oms.dto.request.RegisterRequest;
import com.amtraders.oms.dto.request.ResetPasswordRequest;
import com.amtraders.oms.dto.response.AuthResponse;
import com.amtraders.oms.entity.User;

public interface UserService {

    AuthResponse registerUser(RegisterRequest request);

    AuthResponse loginUser(LoginRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);

    User getUserById(Long id);

    User createUser(User user); // kept for backward compatibility if needed

}
