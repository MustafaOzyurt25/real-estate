package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.realestate.entity.enums.LogType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "logsAdvert")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LogAdvert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LogType log;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt = LocalDateTime.now();

    @ManyToOne
    @JsonIgnore
    private User user;


    @ManyToOne
    @JsonIgnore
    private Advert advert;
}
