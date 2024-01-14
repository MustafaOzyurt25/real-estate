package com.realestate.payload.mappers;

import com.realestate.entity.Advert;
import com.realestate.entity.Role;
import com.realestate.entity.TourRequest;
import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import com.realestate.payload.request.RegisterRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.payload.response.UserResponse;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

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
                .logs(user.getLogs())
                .tourRequestGuests(user.getTourRequestGuest())
                .tourRequestOwners(user.getTourRequestsOwner())
                .build();
    }

    public UserResponse mapUserToUserResponseWithAdvert(User user, List<AdvertResponse> advertList, List<TourRequestResponse> tourRequests) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .favorites(user.getFavorites())
                .adverts(advertList)
                .tourRequests(tourRequests)
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
                .passwordHash(user.getPasswordHash())
                .role(userRequest.getRole())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .build();

    }

}
