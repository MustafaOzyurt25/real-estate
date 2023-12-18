package com.realestate.payload.validator;

import com.realestate.entity.User;
import com.realestate.exception.ConflictException;
import com.realestate.messages.ErrorMessages;
import com.realestate.payload.request.PasswordUpdatedRequest;
import com.realestate.payload.request.UserRequest;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

    private  final UserRepository userRepository;


    public void checkUniqueProperties(User user, UserRequest userRequest){


        String updatedPhone = "";
        String updatedEmail = "";
        boolean isChanged = false;



        if(!user.getPhone().equalsIgnoreCase(userRequest.getPhone())){
            updatedPhone = userRequest.getPhone();
            isChanged = true;
        }

        if (!user.getEmail().equalsIgnoreCase(userRequest.getEmail())){
            updatedEmail = userRequest.getEmail();
            isChanged = true;
        }

        if (isChanged) {
                checkDuplicate(updatedPhone,updatedEmail);
        }


    }
    public void checkDuplicate(String phone,String email) {

       if(userRepository.existsByPhone(phone)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE,phone)) ;
       }
        if(userRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL,email)) ;
        }
    }

}
