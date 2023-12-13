package com.realestate.contactmessage;

import com.realestate.entity.Contact;
import com.realestate.exception.ConflictException;
import com.realestate.messages.ErrorMessages;
import com.realestate.payload.mappers.ContactMapper;
import com.realestate.payload.request.ContactRequest;
import com.realestate.payload.response.ContactResponse;
import com.realestate.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ResponseMessage<ContactResponse> save(ContactRequest contactRequest) {
        boolean isSameMessageWithSameEmailForToday =
                contactRepository.existsByEmailEqualsAndCreateAtEquals(contactRequest.getEmail(), LocalDateTime.now());

        if (isSameMessageWithSameEmailForToday) {
            throw new ConflictException(ErrorMessages.ALREADY_SEND_A_MESSAGE_TODAY);

        }
        //DTO > POJO
        Contact contact = contactMapper.createContact(contactRequest);
        Contact savedData = contactRepository.save(contact);

        return ResponseMessage.<ContactResponse>builder()
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(contactMapper.createResponse(savedData))
                .build();
    }


}
