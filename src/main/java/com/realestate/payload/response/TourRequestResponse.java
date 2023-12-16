package com.realestate.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realestate.entity.enums.TourRequestStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TourRequestResponse {


    private Long tourRequestId;
    private LocalDate tourDate;

    private LocalTime tourTime;
    private TourRequestStatus status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


}
