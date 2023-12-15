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
                .first_name(userRequest.getFirstName())
                .last_name(userRequest.getLastName())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .password_hash(passwordEncoder.encode(userRequest.getPassword()))
                .create_at(LocalDateTime.now())
                .build();
    }

    public UserResponse mapUserToUserResponse(User user){
        return UserResponse.builder()
                .firstName(user.getFirst_name())
                .lastName(user.getLast_name())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }

}
