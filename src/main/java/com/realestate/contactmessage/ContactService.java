package com.realestate.contactmessage;

import com.realestate.entity.Contact;
import com.realestate.entity.TourRequest;
import com.realestate.entity.enums.ContactStatus;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.ContactMapper;
import com.realestate.payload.request.ContactRequest;
import com.realestate.payload.response.ContactResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.payload.response.TourRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    //J01 contactMessageCreated ------------------------------------------------------------------------------
    public ResponseMessage<ContactResponse> contactMessageCreated(ContactRequest contactRequest) {
        boolean isSameMessageWithSameEmailForToday =
                contactRepository.existsByEmailEqualsAndCreateAtEquals(contactRequest.getEmail(), LocalDate.now());

        if (isSameMessageWithSameEmailForToday) {
            throw new ConflictException(ErrorMessages.ALREADY_SEND_A_MESSAGE_TODAY);

        }
        //DTO > POJO
        Contact contact = contactMapper.createContact(contactRequest);
        contact.setStatus(ContactStatus.NOTOPENED);
        Contact savedData = contactRepository.save(contact);

        return ResponseMessage.<ContactResponse>builder()
                .message(SuccessMessages.CONTACT_MESSAGE_CREATED)
                .httpStatus(HttpStatus.CREATED)
                .object(contactMapper.createResponse(savedData))
                .build();
    }


    //J02 getAllContactMessageAsPage -----------------------------------------------------------------------------------
    @Transactional
    public Page<ContactResponse> getAllContactMessageAsPage(int page, int size, String sort, String type, String query) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        if (query == null || query.isEmpty()) {
            return contactRepository.findAll(pageable).map(contactMapper::createResponse);
        }
        List<ContactResponse> contactResponsesList = contactRepository.findAll(pageable)
                .stream()
                .filter(Contact -> Contact.getMessage().toLowerCase().contains(query.toLowerCase()))
                .map(contactMapper::createResponse)
                .toList();

        return new PageImpl<>(contactResponsesList, pageable, contactResponsesList.size());
    }

    //J03
    public ResponseMessage<ContactResponse> getContactMessageById(Long id) {
        Contact contact=getContactById(id);
        contact.setStatus(ContactStatus.OPENED);
        return ResponseMessage.<ContactResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(contactMapper.createResponse(contact))
                .message(SuccessMessages.CONTACT_MESSAGE_FOUNDED).build();
    }

    //J04
    public ResponseMessage deleteContactMessageById(Long id) {
        //id kontrol
        isContactMessageExists(id);
        contactRepository.deleteById(id);
        return ResponseMessage.builder()
                .message(SuccessMessages.CONTACT_MESSAGE_DELETED)
                .httpStatus(HttpStatus.OK).build();
    }

    private Contact getContactById(Long id) {
       return isContactMessageExists(id);
    }

    private Contact isContactMessageExists(Long id) {
        return contactRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.CONTACT_MESSAGE_NOT_FOUND_EXCEPTION,id)));
    }


}
