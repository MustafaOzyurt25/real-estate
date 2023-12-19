package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.entity.enums.TourRequestStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "tour_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TourRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate tourDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "HH:mm:ss")
    private LocalTime tourTime;

    @Enumerated(EnumType.ORDINAL)
    private TourRequestStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;

    @JsonIgnore
    @ManyToOne
    @JsonIgnore
    private Advert advert;

    @ManyToOne
    private User ownerUser;

    @ManyToOne
    private User guestUser;

}
