package com.realestate.security.service;


import com.realestate.entity.Role;
import com.realestate.entity.User;
import com.realestate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Set;



@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{


    private  final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

      User user =userRepository.findByEmailEquals(email);

      if(user != null){
          return new UserDetailsImpl(
                  user.getId(),
                  user.getFirst_name(),
                  user.getEmail(),
                  user.getPassword_hash(),
                  user.getRole().stream().map(Role::getRole_name).toString()
          );
      }
      throw  new UsernameNotFoundException("User" + email + "not found");


    }
}
