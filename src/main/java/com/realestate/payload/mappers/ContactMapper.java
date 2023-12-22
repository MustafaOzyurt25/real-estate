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
                .firstName(contactRequest.getFirstName())
                .lastName(contactRequest.getLastName())
                .message(contactRequest.getMessage())
                .email(contactRequest.getEmail())
                .createAt(LocalDateTime.now())
                .build();
    }

    //POJO > DTO
    public ContactResponse createResponse(Contact contact) {
        return ContactResponse.builder()
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .message(contact.getMessage())
                .email(contact.getEmail())
                .createAt(contact.getCreateAt())
                .build();
    }
}
