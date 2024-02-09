package com.realestate.service;

import com.realestate.entity.Contact;
import com.realestate.entity.enums.ContactStatus;
import com.realestate.exception.ConflictException;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.messages.ErrorMessages;
import com.realestate.messages.SuccessMessages;
import com.realestate.payload.mappers.ContactMapper;
import com.realestate.payload.request.ContactRequest;
import com.realestate.payload.response.ContactResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    /**
     * J01 contactMessageCreated start
     *************************************************************************/
    public void checkIfMessageSentToday(String email) {
        // Güncel tarih ve saat bilgisini al
        LocalDateTime now = LocalDateTime.now();

        // Güncel tarihi al (LocalDate)
        LocalDate today = now.toLocalDate();

        // Belirli bir email ve güncel LocalDate için kayıtlı bir ContactMessage var mı kontrol et
        boolean isSameMessageWithSameEmailForToday =
                contactRepository.existsByEmailEqualsAndCreateAtEquals(email, today);

        if (isSameMessageWithSameEmailForToday) {
            throw new ConflictException(ErrorMessages.ALREADY_SEND_A_MESSAGE_TODAY);
        }
    }

    public ResponseMessage<ContactResponse> contactMessageCreated(ContactRequest contactRequest) {

        checkIfMessageSentToday(contactRequest.getEmail());//e-mail-today kontrolu: basarili

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


    /**J02 getAllContactMessageAsPage start ***********************************************************************/

    public Page<ContactResponse> getAllContactMessageAsPage(String query, int page, int size, String sort, String type, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<ContactResponse> contactResponsesList = contactRepository.findAll(pageable)
                .stream()
                .filter(contact -> statusFilter(contact, status))
                .filter(contact -> query == null || contact.getMessage().toLowerCase().contains(query.toLowerCase()))
                .map(contactMapper::createResponse)
                .toList();

        return new PageImpl<>(contactResponsesList, pageable, contactResponsesList.size());
    }

    private boolean statusFilter(Contact contact, String status) {
        return (status == null || status.equalsIgnoreCase("all")) ||
                (status.equalsIgnoreCase("read") && contact.getStatus().equals(ContactStatus.OPENED)) ||
                (status.equalsIgnoreCase("unread") && contact.getStatus().equals(ContactStatus.NOTOPENED));
    }



    //J03
    public ResponseMessage<ContactResponse> getContactMessageById(Long id) {//bu method ile ilgilen --------------------------------------
        Contact contact = getContactById(id);
        contact.setStatus(ContactStatus.OPENED);
        contactRepository.save(contact);//degisen status durumu save edildi
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
        return contactRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.CONTACT_MESSAGE_NOT_FOUND_EXCEPTION, id)));
    }


}