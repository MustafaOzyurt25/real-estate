package com.realestate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LoginRequest {
    @NotNull(message = "email mustn't be empty")
    private String email;

    @NotNull(message = "password mustn't be empty")
    private String password;
}
