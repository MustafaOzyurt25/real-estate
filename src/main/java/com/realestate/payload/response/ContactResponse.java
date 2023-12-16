package com.realestate.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

//TODO BU CLASS A GEREK VAR MI? -------------------------------------
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactResponse implements Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String message;
    private LocalDateTime createAt;


}
