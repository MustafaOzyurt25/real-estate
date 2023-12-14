package com.realestate.payload.validator;

import com.realestate.entity.User;
import com.realestate.exception.ConflictException;
import com.realestate.messages.ErrorMessages;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private  final UserRepository userRepository;

    public void checkDuplicate(String phone,String email) {

       if(userRepository.existsByPhone(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE,phone)) ;
       }
        if(userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL,email)) ;
        }
    }

}
