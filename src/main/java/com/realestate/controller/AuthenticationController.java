package com.realestate.controller;

import com.realestate.payload.request.ForgotPasswordRequest;
import com.realestate.payload.request.LoginRequest;
import com.realestate.payload.request.RegisterRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.AuthResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.service.AuthenticationService;
import com.realestate.service.ForgotPasswordService;
import com.realestate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return authenticationService.authenticateUser(loginRequest);

    }

    @PostMapping("/register")
    public ResponseMessage<UserResponse> registerUser (@RequestBody @Valid RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPasswordUser(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest){
            forgotPasswordService.forgotPasswordUser(forgotPasswordRequest);
        return ResponseEntity.ok("Password reset request has been initiated. Check your email for further instructions.");
    }



}
