package com.realestate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "username mustn't be empty")
    private String email;

    @NotNull(message = "password mustn't be empty")
    private String password;

}
