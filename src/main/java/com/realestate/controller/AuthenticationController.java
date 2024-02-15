package com.realestate.controller;

import com.realestate.payload.request.*;
import com.realestate.payload.response.AuthResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.service.AuthenticationService;
import com.realestate.service.ForgotPasswordService;
import com.realestate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ForgotPasswordService forgotPasswordService;

    //F01
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return authenticationService.authenticateUser(loginRequest);

    }
    //F02
    @PostMapping("/register")
    public ResponseMessage<UserResponse> registerUser (@RequestBody @Valid RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest);
    }
    //F03
    @PostMapping("/forgot-password")
    public ResponseEntity forgotPasswordUser(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest){
            forgotPasswordService.forgotPasswordUser(forgotPasswordRequest);
        return ResponseEntity.ok("Password reset request has been initiated. Check your email for further instructions.");
    }
    //F04
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String resetToken , @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        try {
            forgotPasswordService.resetPassword(resetToken,resetPasswordRequest);
            return ResponseEntity.ok("Password reset successful!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/google/register")
    public ResponseMessage<UserResponse> loginUserWithGoogle(@RequestBody @Valid LoginRequestWithGoogle loginRequestWithGoogle){

        return userService.loginUserWithGoogle(loginRequestWithGoogle);

    }

    @PostMapping("/google/login")
    public ResponseEntity<AuthResponse> authenticateUserWithGoogle(@RequestBody @Valid LoginRequestWithGoogle loginRequestWithGoogle){

        return authenticationService.authenticateUserWithGoogle(loginRequestWithGoogle);

    }









}
