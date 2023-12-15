package com.realestate.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.entity.Role;
import com.realestate.entity.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRequest
{
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Pattern(regexp = "\\(\\d{3}\\) \\d{3}-\\d{4}", message = "Invalid phone format")
    @NotNull
    private String phone;

    @Email(message = "Invalid email format")
    @NotNull
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Invalid password format"
    )
    @NotNull
    private String password;


}
