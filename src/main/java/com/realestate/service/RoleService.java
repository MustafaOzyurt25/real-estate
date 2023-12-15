package com.realestate.service;

import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.entity.enums.RoleType;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.RoleRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.realestate.messages.ErrorMessages.RESOURCE_CONFLICT_EXCEPTION;
import static com.realestate.messages.ErrorMessages.RESOURCE_NOT_FOUND_EXCEPTION;


@RequiredArgsConstructor
@Service
public class RoleService
{
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Role getRole(RoleType roleType)
    {
        return roleRepository.findByEnumRoleEquals(roleType).orElseThrow(() ->
                new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND_EXCEPTION , "Role")));
    }

    public List<Role> getAllUserRoles()
    {
        return roleRepository.findAll();
    }

    public Role saveRole(RoleType roleType)
    {
        if(roleRepository.existsByEnumUserRole((roleType)))
        {
            throw new ConflictException(String.format(RESOURCE_CONFLICT_EXCEPTION , roleType));
        }

        Role role = Role.builder().role_name(roleType).build();
        roleRepository.save(role);
        return role;
    }

    public Set<String> getUserRoles(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(user -> user.getRole()
                        .stream()
                        .map(Role::getRole_name)
                        .map(Enum::name)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

    }


}
