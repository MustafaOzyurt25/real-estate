package com.realestate.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private LocalDate tour_date;

    private LocalTime tour_time;
    private TourRequestStatus status;
    private LocalDateTime create_at;
    private LocalDateTime update_at;


}
