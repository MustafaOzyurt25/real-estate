package com.realestate.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

//TODO BU CLASS A GEREK VAR MI? -------------------------------------
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactResponse implements Serializable {

    private String first_name;
    private String last_name;
    private String email;
    private String message;
    private LocalDateTime create_at;


}
