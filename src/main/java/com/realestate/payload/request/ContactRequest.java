package com.realestate.payload.request;

import com.realestate.entity.Contact;
import com.realestate.entity.enums.ContactStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.function.Supplier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactRequest implements Serializable  {

    @NotNull(message = "Please enter first name.")
    @Size(min=2, max = 30, message = "Your first name should be at least 4 characters.")
    @Pattern(regexp= "\\A(?!\\s*\\Z).+",message = "Your message must consist of the character .")
    private String firstName;

    @NotNull(message = "Please enter last name.")
    @Size(min=2, max = 30, message = "Your last name should be at least 4 characters.")
    @Pattern(regexp= "\\A(?!\\s*\\Z).+",message = "Your message must consist of the character .")
    private String lastName;

    @Email(message = "Please enter valid email.")
    @Size(min=5, max=60, message = "Your email should be at least 5 characters.")
    @NotNull(message = "Please enter your email.")
    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email; //@Email(message = "{validation.email.pattern}") sadece bu yeterli

    @Size(min=4, max=300, message = "Your message should be at least 4 characters.")
    @NotNull(message = "Please enter message.")
    @Pattern(regexp= "\\A(?!\\s*\\Z).+",message = "Your message must consist of the character .")
    private String message;

    private ContactStatus status;




}
