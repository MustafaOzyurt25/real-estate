package com.realestate.service;

import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.UserMapper;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.payload.validator.UniquePropertyValidator;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;



    public void saveDefaultAdmin(User defaultAdmin)
    {
        Set<Role> role = new HashSet<>();
        role.add(roleService.getRole(RoleType.ADMIN));

        defaultAdmin.setFirstName("admin");
        defaultAdmin.setLastName("admin");
        defaultAdmin.setCreateAt(LocalDateTime.now());
        defaultAdmin.setPasswordHash(passwordEncoder.encode("123456Aa*"));
        defaultAdmin.setEmail("admin10@gmail.com");
        defaultAdmin.setPhone("555-555-5555");
        defaultAdmin.setRole(role);
        defaultAdmin.setBuiltIn(true);
        userRepository.save(defaultAdmin);
    }

    public ResponseMessage<UserResponse> registerUser(UserRequest userRequest) {

        uniquePropertyValidator.checkDuplicate(userRequest.getPhone(),userRequest.getEmail());
        User user = userMapper.mapUserRequestToUser(userRequest);

        Set<Role> role = new HashSet<>();
        role.add(roleService.getRole(RoleType.CUSTOMER));
        user.setBuiltIn(false);
        user.setRole(role);


        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse(userRepository.save(user)))
                .message(SuccessMessages.USER_CREATE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }
}
