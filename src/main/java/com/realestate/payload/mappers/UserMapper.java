package com.realestate.payload.mappers;

import com.realestate.entity.User;
import com.realestate.payload.request.RegisterRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.AdvertResponse;
import com.realestate.payload.response.FavoriteResponse;
import com.realestate.payload.response.TourRequestResponse;
import com.realestate.payload.response.UserResponse;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final FavoriteMapper favoriteMapper;


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
                .roles(user.getRole())
                .favoriteList(user.getFavorites().stream().map(favoriteMapper::mapToFavoriteToFavoriteResponse).collect(Collectors.toList()))
                .logUser(user.getLogUser())
                .tourRequestGuests(user.getTourRequestGuest())
                .tourRequestOwners(user.getTourRequestsOwner())
                .build();
    }

    public UserResponse mapUserToUserResponseWithAdvert(User user, List<AdvertResponse> advertList, List<TourRequestResponse> tourRequests, List<FavoriteResponse> favorites) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .favoriteList(favorites)
                .adverts(advertList)
                .tourRequests(tourRequests)
                .logAdverts(user.getLogAdverts())
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
