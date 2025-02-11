package com.realestate.payload.mappers;

import com.realestate.entity.Favorite;
import com.realestate.entity.User;
import com.realestate.payload.request.RegisterRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.*;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final FavoriteMapper favoriteMapper;
    private final LogUserMapper logUserMapper;


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
        List<FavoriteResponse> favoriteResponses = new ArrayList<>();
        List<LogUserResponse> logUserResponses = new ArrayList<>();
        if (user.getFavorites() != null && !user.getFavorites().isEmpty()){
            favoriteResponses = user.getFavorites().stream().map(favoriteMapper::mapToFavoriteToFavoriteResponse).toList();
        }
        if (user.getLogUser() != null && !user.getLogUser().isEmpty()){
            logUserResponses = user.getLogUser().stream().map(logUserMapper::mapLogUserToLogUserResponse).collect(Collectors.toList());
        }

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .roles(user.getRole())
                .favoriteList(favoriteResponses)
                .logUser(logUserResponses)
                .tourRequestGuests(user.getTourRequestGuest())
                .tourRequestOwners(user.getTourRequestsOwner())
                .build();
    }

    public UserResponse mapUserToUserResponseWithAdvert(User user, List<AdvertResponse> advertList, List<TourRequestResponse> tourRequests, List<FavoriteResponse> favorites) {
        List<LogUserResponse> logUserResponses = new ArrayList<>();
        if (user.getLogUser() != null && !user.getLogUser().isEmpty()){
            logUserResponses = user.getLogUser().stream().map(logUserMapper::mapLogUserToLogUserResponse).collect(Collectors.toList());
        }
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
                .logUser(logUserResponses)
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
