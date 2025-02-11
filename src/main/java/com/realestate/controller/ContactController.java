package com.realestate.controller;

import com.realestate.payload.request.ContactRequest;
import com.realestate.payload.response.ContactResponse;
import com.realestate.payload.response.ResponseMessage;
import com.realestate.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contact-messages")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    //J01 save----------------------------------------------------------------------------------------------------------
    @PostMapping("/save")
    public ResponseMessage<ContactResponse> contactMessageCreated(@RequestBody
                                                                  @Valid ContactRequest contactRequest) {
        return contactService.contactMessageCreated(contactRequest);
    }

    //J02 getAllContactMessageAsPage -----------------------------------------------------------------------------------
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping()
    public Page<ContactResponse>  getAllContactMessageAsPage(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "sort", defaultValue = "createAt", required = false) String sort,//tarihe gore sirala
            @RequestParam(value = "type", defaultValue = "asc", required = false) String type,
            @RequestParam(value = "status", required = false, defaultValue = "unread") String status

    ) {
        return contactService.getAllContactMessageAsPage(query, page, size, sort, type, status);
    }

    /**deleteAllContactMessages-------------------*/

    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping()
    public ResponseEntity<?> deleteAllMessages() {
        return contactService.deleteAllContactMessages();
    }


    /**updateStatusContactMessages--------------------*/

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PatchMapping("/{id}")
    public ResponseMessage<ContactResponse> updateContactMessages(@PathVariable Long id){
        return contactService.updateContactMessages(id);
    }



    //J03

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @GetMapping("/{id}")

    public ResponseMessage<ContactResponse> getContactMessageById(@PathVariable Long id){
        return contactService.getContactMessageById(id);
    }

    //J04
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteContactMessageById (@PathVariable Long id){
        return contactService.deleteContactMessageById(id);
    }

}