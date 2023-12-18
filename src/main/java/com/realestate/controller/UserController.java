package com.realestate.controller;

import com.realestate.entity.User;
import com.realestate.payload.request.PasswordUpdatedRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/auth/delete")
    public ResponseMessage authenticatedUserDeleted(HttpServletRequest request){
        return userService.authenticatedUserDeleted(request);
    }

    @GetMapping("/admin")
    public Page<UserResponse> getAllUsersByPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestParam(value = "sort", defaultValue = "firstName") String sort,
                                                @RequestParam(value = "type", defaultValue = "desc") String type)
    {
        return userService.getAllUsersByPage(page,size,sort,type);
    }

    @GetMapping("/{userId}/admin")
    public ResponseMessage<UserResponse> getUserByAdmin(@PathVariable Long userId){
        return  userService.getUserByAdmin(userId);
    }



}
