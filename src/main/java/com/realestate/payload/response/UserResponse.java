package com.realestate.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realestate.entity.Favorite;
import com.realestate.entity.Log;
import com.realestate.entity.Role;
import com.realestate.entity.TourRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(toBuilder = true)
public class UserResponse
{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime updateAt;
    private LocalDateTime createAt;
    private List<Favorite> favorites;
    private List<Log> logs;
    private Set<Role> roles;
    private List<TourRequest> tourRequestOwners;
    private List<TourRequest> tourRequestGuests;



}
