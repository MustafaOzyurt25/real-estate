package com.realestate.payload.mappers;

import com.realestate.entity.User;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.UserResponse;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class UserMapper {

    private final PasswordEncoder passwordEncoder;


    public User mapUserRequestToUser(UserRequest userRequest){
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .passwordHash(passwordEncoder.encode(userRequest.getPassword()))
                .createAt(LocalDateTime.now())
                .build();
    }

    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
}
