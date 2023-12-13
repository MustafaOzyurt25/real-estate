package com.realestate.payload.mappers;

import com.realestate.entity.Contact;
import com.realestate.payload.request.ContactRequest;
import com.realestate.payload.response.ContactResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContactMapper {

    //HELPER METHODS
    //DTO > POJO
    public Contact createContact(ContactRequest contactRequest) {
        return Contact.builder()
                .first_name(contactRequest.getFirst_name())
                .last_name(contactRequest.getLast_name())
                .message(contactRequest.getMessage())
                .email(contactRequest.getEmail())
                .create_at(LocalDateTime.now())
                .build();
    }

    //POJO > DTO
    public ContactResponse createResponse(Contact contact) {
        return ContactResponse.builder()
                .first_name(contact.getFirst_name())
                .last_name(contact.getLast_name())
                .message(contact.getMessage())
                .email(contact.getEmail())
                .create_at(LocalDateTime.now())
                .build();
    }
}
