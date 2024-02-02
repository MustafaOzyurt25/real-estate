package com.realestate.payload.response;

import com.realestate.entity.enums.ContactStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactResponse implements Serializable {

    private Long id;//frontend j02 icin eklendi
    private String firstName;
    private String lastName;
    private String email;
    private String message;
    private LocalDateTime createAt;
    private ContactStatus contactStatus;



}
