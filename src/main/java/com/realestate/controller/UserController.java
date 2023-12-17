package com.realestate.controller;

import com.realestate.entity.User;
import com.realestate.payload.request.PasswordUpdatedRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUser(HttpServletRequest httpServletRequest){

        return userService.authenticatedUser(httpServletRequest);
    }

    @PutMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUserUpdated(HttpServletRequest httpServletRequest, @RequestBody @Valid UserRequest userRequest){

        return userService.authenticatedUserUpdated(httpServletRequest,userRequest);
    }

    @PatchMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUserPasswordUpdated(HttpServletRequest httpServletRequest, @RequestBody @Valid PasswordUpdatedRequest passwordUpdatedRequest){
        return userService.authenticatedUserPasswordUpdated(httpServletRequest,passwordUpdatedRequest);
    }



}
