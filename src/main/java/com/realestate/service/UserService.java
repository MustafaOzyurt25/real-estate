package com.realestate.service;

import com.realestate.entity.User;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;

    public void saveDefaultAdmin(User defaultAdmin)
    {
        defaultAdmin.setBuilt_in(true);
        userRepository.save(defaultAdmin);

    }
}
