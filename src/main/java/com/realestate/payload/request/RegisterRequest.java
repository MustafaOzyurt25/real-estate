package com.realestate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RegisterRequest {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your phoneNumber must consist of the characters .")
    @Size(min=12,max=12, message="Your phoneNumber should be 12 chars long")
    @NotNull
    private String phone;

    @Email(message = "Invalid email format")
    @NotNull
    private String email;

    @Pattern(
           // regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
              regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&.]{8,}$",
            message = "Invalid password format"
    )
    @NotNull
    private String password;
}
