package com.realestate.contactmessage;

import com.realestate.payload.request.ContactRequest;
import com.realestate.payload.response.ContactResponse;
import com.realestate.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/contactMessage")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    //save--------------------------------------------------------------------------------------------------------------
    @PostMapping("/save")
    public ResponseMessage<ContactResponse> save(@RequestBody
                                                 @Valid ContactRequest contactRequest) {
        return contactService.save(contactRequest);
    }
}

/*
contactmessage objesi save methodu
role based
preouthorized da has any authority de anonymous, rol koymayacagim.

entity
entity e request olustur service e al
service de mapper olustur
mapper request i pojoya donustursun
pojo repository e ulas
db ye save yap

controller da respons entity icine ? yaz

service de string dönebilirsin: "Contact Message created successfully!"

 */