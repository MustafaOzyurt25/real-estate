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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN','MANAGER')")
    @GetMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUser(HttpServletRequest httpServletRequest){

        return userService.authenticatedUser(httpServletRequest);
    }

   //@PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN','MANAGER')")
    @PutMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUserUpdated(HttpServletRequest httpServletRequest, @RequestBody @Valid UserRequest userRequest){

        return userService.authenticatedUserUpdated(httpServletRequest,userRequest);
    }
    @PreAuthorize("hasAnyAuthority('CUSTOMER','ADMIN','MANAGER')")
    @PatchMapping("/auth")
    public ResponseMessage<UserResponse> authenticatedUserPasswordUpdated(HttpServletRequest httpServletRequest, @RequestBody @Valid PasswordUpdatedRequest passwordUpdatedRequest){
        return userService.authenticatedUserPasswordUpdated(httpServletRequest,passwordUpdatedRequest);
    }

    @DeleteMapping("/auth/delete")
    public ResponseMessage authenticatedUserDeleted(HttpServletRequest request){
        return userService.authenticatedUserDeleted(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin")
    public Page<UserResponse> getAllUsersByPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestParam(value = "sort", defaultValue = "firstName") String sort,
                                                @RequestParam(value = "type", defaultValue = "desc") String type)
    {
        return userService.getAllUsersByPage(page,size,sort,type);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{userId}/admin")
    public ResponseMessage<UserResponse> getUserByAdmin(@PathVariable Long userId){
        return  userService.getUserByAdmin(userId);
    }

    @DeleteMapping("/{userId}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN' , 'MANAGER')")
    public ResponseMessage<UserResponse> deleteUser(@PathVariable("userId") Long userId , HttpServletRequest request)
    {
        return userService.deleteUserById(userId , request);
    }

    /*
    //F11 user update
    @PutMapping("{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN' , 'MANAGER')")
    public ResponseEntity<UserResponse> updateUserById(
            @PathVariable("id") Long id,
            HttpServletRequest request)
    {
        return userService.updateUserById(id, request);
    }

     */


}
