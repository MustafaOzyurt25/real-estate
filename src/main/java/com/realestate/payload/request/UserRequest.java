package com.realestate.payload.request;

import com.realestate.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRequest
{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Role role;
}
