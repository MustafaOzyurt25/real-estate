package com.realestate.service;

import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.helper.PageableHelper;
import com.realestate.payload.mappers.UserMapper;
import com.realestate.payload.request.PasswordUpdatedRequest;
import com.realestate.payload.request.RegisterRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.UserResponse;
import com.realestate.payload.validator.UniquePropertyValidator;
import com.realestate.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    private final PageableHelper pageableHelper;


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


    public List<User> getALlUsers() {
        return userRepository.findAll();
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

    public ResponseMessage<UserResponse> authenticatedUserUpdated(HttpServletRequest request, UserRequest userRequest) {

        String userEmail = (String) request.getAttribute("email");

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

    public ResponseMessage<UserResponse> authenticatedUserPasswordUpdated(HttpServletRequest request, PasswordUpdatedRequest passwordUpdatedRequest) {

        String userEmail = (String) request.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);


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


    public ResponseMessage authenticatedUserDeleted(HttpServletRequest request) {

        String userEmail = (String) request.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);

        //user in advert veya tour request i varsa silmeye izin verilmeyecek

        if(user.getBuiltIn().equals(false)){

            userRepository.deleteById(user.getId());
        }

        else throw new ConflictException("You do not have permission to delete this user");

        return ResponseMessage.builder()
                .message(SuccessMessages.USER_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public Page<UserResponse> getAllUsersByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse);
    }

    public ResponseMessage<UserResponse> getUserByAdmin(Long userId) {

        User user = isUserExists(userId);

        //user in adverti da frontend de gozuktugu icin response da olmasi gerekiyor

        return ResponseMessage.<UserResponse>builder()
                .object(userMapper.mapUserToUserResponse(user))
                .message(SuccessMessages.USER_FOUNDED)
                .httpStatus(HttpStatus.OK)
                .build();

    }

    private User isUserExists(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId)));

    }
}
