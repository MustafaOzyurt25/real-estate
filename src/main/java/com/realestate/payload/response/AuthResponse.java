package com.realestate.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)//null olanlari gondermeyecek
public class AuthResponse {


    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
    private String token;



}