package com.realestate.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realestate.entity.enums.TourRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTourRequestResponse {
    private Long tourRequestId;
    private LocalDate tourDate;
    private LocalTime tourTime;
}
