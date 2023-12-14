package com.realestate.service;

import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.UserMapper;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.payload.validator.UniquePropertyValidator;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UniquePropertyValidator uniquePropertyValidator;

    public void saveDefaultAdmin(User defaultAdmin)
    {
        defaultAdmin.setBuilt_in(true);
        userRepository.save(defaultAdmin);

    }

    public ResponseMessage<UserResponse> registerUser(UserRequest userRequest) {

        uniquePropertyValidator.checkDuplicate(userRequest.getPhone(),userRequest.getEmail());
        User user = userMapper.mapUserRequestToUser(userRequest);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.builder()
                .role_name(RoleType.CUSTOMER)
                .build());
        user.setRole(roleSet);


        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse(userRepository.save(user)))
                .message(SuccessMessages.USER_CREATE)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }
}
