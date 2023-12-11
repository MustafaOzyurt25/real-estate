package com.realestate.payload.request;

import com.realestate.entity.Advert;
import com.realestate.entity.enums.TourRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TourRequestRequest {

    @NotNull(message = "Please enter advert id")
    private Advert advertId;

    @NotNull(message = "Please enter tour date")
    private LocalDate tour_date;
    @NotNull(message = "Please enter tour time")
    private LocalTime tour_time;
    @NotNull(message = "Please enter status")
    private TourRequestStatus status;

    private LocalDateTime create_at;
    private LocalDateTime update_at;
}
