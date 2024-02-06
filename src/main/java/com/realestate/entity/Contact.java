package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.realestate.entity.enums.ContactStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;

    //@Column(unique = true) --> e-mail contact message icin unique olmamali. sadece ayni gun icinde message atma kisitlamasi var
    @Column(unique = true)
    private String email;
    private String message;
    @Enumerated(EnumType.ORDINAL)
    private ContactStatus status;


    @Column(name = "create_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt = LocalDateTime.now();

}
