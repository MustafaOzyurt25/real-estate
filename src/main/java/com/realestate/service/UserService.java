package com.realestate.service;

import com.realestate.entity.Advert;
import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import com.realestate.exception.BadRequestException;
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
import com.realestate.repository.RoleRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
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
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final AdvertService advertService;
    private final TourRequestsService tourRequestsService;
    private final FavoritesService favoritesService;
    private final LogsService logsService;


    public void saveDefaultAdmin(User defaultAdmin) {
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

        uniquePropertyValidator.checkDuplicate(registerRequest.getPhone(), registerRequest.getEmail());

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

        if (user.getBuiltIn().equals(false)) {

            uniquePropertyValidator.checkUniqueProperties(user, userRequest);

            User updatedUser = userMapper.mapUserRequestUpdatedUser(user, userRequest);

            updatedUser.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

            User savedUser = userRepository.save(updatedUser);

            return ResponseMessage.<UserResponse>builder()
                    .object(userMapper.mapUserToUserResponse(savedUser))
                    .message(SuccessMessages.USER_UPDATE)
                    .httpStatus(HttpStatus.OK)
                    .build();

        } else {
            throw new ConflictException("User can not be updated");
        }


    }

    public ResponseMessage<UserResponse> authenticatedUserPasswordUpdated(HttpServletRequest request, PasswordUpdatedRequest passwordUpdatedRequest) {

        String userEmail = (String) request.getAttribute("email");

        User user = userRepository.findByEmailEquals(userEmail);


        if (user.getBuiltIn().equals(false) &&
                passwordEncoder.matches(passwordUpdatedRequest.getCurrentPassword(), user.getPasswordHash()) &&
                passwordUpdatedRequest.getNewPassword().equals(passwordUpdatedRequest.getRetryNewPassword())) {

            String newPassword = passwordUpdatedRequest.getNewPassword();

            user.setPasswordHash(passwordEncoder.encode(newPassword));
            user.setUpdateAt(LocalDateTime.now());

            User savedUser = userRepository.save(user);

            return ResponseMessage.<UserResponse>builder()
                    .object(userMapper.mapUserToUserResponse(savedUser))
                    .message(SuccessMessages.USER_PASSWORD_UPDATE)
                    .httpStatus(HttpStatus.OK)
                    .build();
        } else {
            throw new ConflictException("User can not be updated");
        }

    }

    public ResponseMessage authenticatedUserDeleted(HttpServletRequest request) {

        try {
            String userEmail = (String) request.getAttribute("email");

            User user = userRepository.findByEmailEquals(userEmail);

            if (user.getBuiltIn().equals(false)) {
                userRepository.deleteById(user.getId());
            } else throw new ConflictException("You do not have permission to delete this user");

            return ResponseMessage.builder()
                    .message(SuccessMessages.USER_DELETE)
                    .httpStatus(HttpStatus.OK)
                    .build();


        } catch (RuntimeException e) {
            throw new ConflictException(ErrorMessages.USER_CANNOT_BE_DELETED);
        }

    }

    public Page<UserResponse> getAllUsersByPage(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        return userRepository.findAll(pageable).map(userMapper::mapUserToUserResponse);
    }

    public ResponseMessage<UserResponse> getUserByAdmin(Long userId) {

        User user = isUserExists(userId);

        //F09 un fronted sayfasinda advert gozukmuyor eger lazim olursa tekrar kontrol edecegiz
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

    public ResponseMessage<UserResponse> deleteUserById(Long userId, HttpServletRequest request) {
        String userInSessionEmail = (String) request.getAttribute("email");
        User userInSession = userRepository.findByEmailEquals(userInSessionEmail); // silme işlemi yapacak olan kullanıcı
        Set<String> roles = userRepository.getRolesById(userInSession.getId()); // silme işlemini yapacak olan kişinin rolleri

        System.out.println("silmek isteyen kullanıcı rolleri : " + roles);
        if (!roles.contains("ADMIN")) {
            if (roles.contains("MANAGER")) {
                // Silinmek istenen user'ın rollerine bakmak lazım.
                Set<String> rolesOfUserWhoWantedToDelete = userRepository.getRolesById(userId);
                System.out.println("FIND ROLE BY USER ID : " + rolesOfUserWhoWantedToDelete);

                if (!(rolesOfUserWhoWantedToDelete.contains("CUSTOMER") && !rolesOfUserWhoWantedToDelete.contains("MANAGER") && !rolesOfUserWhoWantedToDelete.contains("ADMIN"))) {
                    throw new BadRequestException(ErrorMessages.MANAGER_CAN_DELETE_ONLY_A_CUSTOMER);
                }
            } else {
                System.out.println("Silmek isteyen kullanıcı CUSTOMER rolünde");
                throw new BadRequestException(ErrorMessages.CUSTOMER_CAN_NOT_DELETE_ANY_USER);

            }
        }

        System.out.println("User In Session : " + userInSessionEmail);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND_EXCEPTION, "User")));
        UserResponse deletedUser = userMapper.mapUserToUserResponse(user);
        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessages.USER_CAN_NOT_DELETE_HAS_BUILT_IN_TRUE_MESSAGE);
        }

        boolean advertControlByUserId = advertService.controlAdvertByUserId(userId); // User'ın advert'ı var mı?
        boolean tourRequestControlByUserId = tourRequestsService.controlTourRequestByUserId(userId);

        if (tourRequestControlByUserId || advertControlByUserId) {
            throw new BadRequestException(ErrorMessages.CAN_NOT_BE_DELETABLE_USER);
        }

        logsService.deleteByUserId(userId); // ÇALIŞIYOR
        favoritesService.deleteByUserId(userId); // ÇALIŞIYOR


        userRepository.deleteById(userId);


        System.out.println("roller" + roles);
        return ResponseMessage.<UserResponse>builder()
                .object(deletedUser)
                .httpStatus(HttpStatus.OK)
                .message(SuccessMessages.USER_DELETED_SUCCESSFULLY)
                .build();
    }

    public void saveManagerForTest() {
        User managerUser = new User();
        Set<Role> managerRoles = new HashSet<>();
        managerRoles.add(roleService.getRole(RoleType.MANAGER));
        managerRoles.add(roleService.getRole(RoleType.CUSTOMER));

        managerUser.setFirstName("Manager");
        managerUser.setLastName("Manager");
        managerUser.setEmail("manager10@gmail.com");
        managerUser.setPhone("505-505-5055");
        managerUser.setPasswordHash(passwordEncoder.encode("123456Aa*"));
        managerUser.setRole(managerRoles);
        userRepository.save(managerUser);

    }
}
