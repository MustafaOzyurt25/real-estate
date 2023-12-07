package com.realestate.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name="adverts")
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String slug;

    private Double price;

    private Integer status;

    private Boolean built_in;

    private Boolean is_active;

    private Integer view_count;

    private String location;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime update_at;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.REMOVE)
    private List<CategoryPropertyValue> categoryPropertyValue;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<TourRequest> tourRequests;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.REMOVE)
    private List<Image> images;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.REMOVE)
    private List<Log> logs;



}
