package com.realestate.service;

import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import com.realestate.exception.ConflictException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.UserMapper;
import com.realestate.payload.request.PasswordUpdatedRequest;
import com.realestate.payload.request.RegisterRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.payload.validator.UniquePropertyValidator;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
        role.add(roleService.getRole(RoleType.CUSTOMER));
        role.add(roleService.getRole(RoleType.MANAGER));

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

    public ResponseMessage<UserResponse> registerUser(RegisterRequest registerRequest) {

        uniquePropertyValidator.checkDuplicate(registerRequest.getPhone(),registerRequest.getEmail());
        User user = userMapper.mapRegisterRequestToUser(registerRequest);

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

    public ResponseMessage<UserResponse> authenticatedUser(HttpServletRequest httpServletRequest) {

        String userEmail = (String) httpServletRequest.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);

        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse(user))
                .message(SuccessMessages.USER_FOUNDED)
                .httpStatus(HttpStatus.CREATED)
                .build();

    }

    public ResponseMessage<UserResponse> authenticatedUserUpdated(HttpServletRequest httpServletRequest, UserRequest userRequest) {

        String userEmail = (String) httpServletRequest.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);

        if(user.getBuiltIn().equals(false)){

            uniquePropertyValidator.checkUniqueProperties(user,userRequest);

            User updatedUser = userMapper.mapUserRequestUpdatedUser(user,userRequest);

            updatedUser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

            User savedUser = userRepository.save(updatedUser);

            return ResponseMessage.<UserResponse>builder()
                    .object(userMapper.mapUserToUserResponse(savedUser))
                    .message(SuccessMessages.USER_UPDATE)
                    .httpStatus(HttpStatus.OK)
                    .build();

        }

        else{
           throw new ConflictException("User can not be updated");
        }


    }

    public ResponseMessage<UserResponse> authenticatedUserPasswordUpdated(HttpServletRequest httpServletRequest, PasswordUpdatedRequest passwordUpdatedRequest) {

        String userEmail = (String) httpServletRequest.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);

        //user.getPasswordHash().equals(passwordEncoder.encode(passwordUpdatedRequest.getCurrentPassword()))

        if(user.getBuiltIn().equals(false) &&
                passwordEncoder.matches(passwordUpdatedRequest.getCurrentPassword(), user.getPasswordHash())&&
           passwordUpdatedRequest.getNewPassword().equals(passwordUpdatedRequest.getRetryNewPassword()))
        {

            String newPassword = passwordUpdatedRequest.getNewPassword();

            user.setPasswordHash(passwordEncoder.encode(newPassword));
            user.setUpdateAt(LocalDateTime.now());

            User savedUser = userRepository.save(user);

            return ResponseMessage.<UserResponse>builder()
                    .object(userMapper.mapUserToUserResponse(savedUser))
                    .message(SuccessMessages.USER_PASSWORD_UPDATE)
                    .httpStatus(HttpStatus.OK)
                    .build();

        }

        else
        {
            throw new ConflictException("User can not be updated");
        }

    }
}
