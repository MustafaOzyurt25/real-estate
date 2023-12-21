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
public class ResetPasswordRequest {

    @NotNull(message = "New Password mustn't be empty")
    private String newPassword;

    @NotNull(message = "Retry New Password mustn't be empty")
    private String retryNewPassword;
}
