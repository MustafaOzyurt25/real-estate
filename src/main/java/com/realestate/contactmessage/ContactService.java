package com.realestate.contactmessage;

import com.realestate.entity.Contact;
import com.realestate.entity.TourRequest;
import com.realestate.exception.ConflictException;
import com.realestate.messages.ErrorMessages;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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


    //J01 getAllContactMessageAsPage -----------------------------------------------------------------------------------
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

}
