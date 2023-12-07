package com.realestate.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;

    private String last_name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password_hash;

    private String reset_password_code;

    private Boolean built_in;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalTime create_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalTime update_at;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Log> logs;


}
