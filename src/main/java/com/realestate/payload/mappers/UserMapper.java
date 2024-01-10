package com.realestate.payload.mappers;

import com.realestate.entity.User;
import com.realestate.payload.request.RegisterRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.UserResponse;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Data
public class UserMapper {

    private final PasswordEncoder passwordEncoder;


    public User mapUserRequestToUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .createAt(LocalDateTime.now())
                .build();
    }

    public User mapRegisterRequestToUser(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phone(registerRequest.getPhone())
                .email(registerRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registerRequest.getPassword()))
                .createAt(LocalDateTime.now())
                .build();
    }

    public UserResponse mapUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .favorites(user.getFavorites())
                .roles(user.getRole())
                .tourRequestGuests(user.getTourRequestGuest())
                .tourRequestOwners(user.getTourRequestsOwner())
                .build();
    }

    public User mapUserRequestUpdatedUser(User user, UserRequest userRequest) {
        return User.builder()
                .id(user.getId())
                .createAt(user.getCreateAt())
                .builtIn(user.getBuiltIn())
                .updateAt(LocalDateTime.now())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .build();

    }

}
