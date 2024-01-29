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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //F05
    @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN','MANAGER')")
    @GetMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUser(HttpServletRequest httpServletRequest){

        return userService.authenticatedUser(httpServletRequest);
    }

    //F06
   @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN','MANAGER')")
    @PutMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUserUpdated(HttpServletRequest httpServletRequest, @RequestBody @Valid UserRequest userRequest){

        return userService.authenticatedUserUpdated(httpServletRequest,userRequest);
    }

    //F07
    @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN','MANAGER')")
    @PatchMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUserPasswordUpdated(HttpServletRequest httpServletRequest, @RequestBody @Valid PasswordUpdatedRequest passwordUpdatedRequest){
        return userService.authenticatedUserPasswordUpdated(httpServletRequest,passwordUpdatedRequest);
    }

    //F08
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    @DeleteMapping("/auth/delete")
    public ResponseMessage authenticatedUserDeleted(HttpServletRequest request){
        return userService.authenticatedUserDeleted(request);
    }

    //F09
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/admin")
    public Page<UserResponse> getAllUsersByPage(HttpServletRequest httpServletRequest,
                                                                 @RequestParam(value = "q", required = false) String q,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                                 @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                                                 @RequestParam(value = "type", defaultValue = "desc") String type)
    {
        return userService.getAllUsersByPage(httpServletRequest,q,page,size,sort,type);
    }

    //F10

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/{userId}/admin")
    public ResponseMessage<UserResponse> getUserByAdmin(@PathVariable Long userId){
        return  userService.getUserByAdmin(userId);
    }
    //F12
    @DeleteMapping("/{userId}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN' , 'MANAGER')")
    public ResponseMessage<UserResponse> deleteUser(@PathVariable("userId") Long userId , HttpServletRequest request)
    {
        return userService.deleteUserById(userId , request);
    }

    //F11 user update
    @PutMapping("{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN' , 'MANAGER')")
    public ResponseMessage<UserResponse> updateUserById(@PathVariable("id") Long id, @RequestBody @Valid UserRequest request)
    {
        return userService.updateUserById(id, request);
    }

}
