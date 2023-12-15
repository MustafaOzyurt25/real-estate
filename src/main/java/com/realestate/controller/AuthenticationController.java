package com.realestate.controller;

import com.realestate.payload.request.LoginRequest;
import com.realestate.payload.response.AuthResponse;
import com.realestate.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }

}
