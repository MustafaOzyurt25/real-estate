package com.realestate.payload.mappers;

import com.realestate.entity.Contact;
import com.realestate.payload.request.ContactRequest;
import com.realestate.payload.response.ContactResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ContactMapper {

    //DTO > POJO
    public Contact createContact(ContactRequest contactRequest) {
        return Contact.builder()
                .firstName(contactRequest.getFirstName())
                .lastName(contactRequest.getLastName())
                .email(contactRequest.getEmail())
                .message(contactRequest.getMessage())
                .createAt(LocalDateTime.now())
                .build();
    }

    //POJO > DTO
    public ContactResponse createResponse(Contact contact) {
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .createAt(contact.getCreateAt())
                .status(contact.getStatus())
                .build();
    }

}
