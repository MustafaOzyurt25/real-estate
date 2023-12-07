package com.realestate.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.realestate.realestate.entity.enums.AdvertStatus;
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

    @Column(unique = true)
    private String title;

    private String description;

    @Column(unique = true)
    private String slug;

    private Double price;

    @Enumerated(EnumType.ORDINAL)
    private AdvertStatus status;

    private Boolean built_in = false;
    private Boolean is_active;
    private Integer view_count;
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at = LocalDateTime.now();

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
    @ManyToOne
    private AdvertType advertType;
    @ManyToOne
    private Country country;
    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;


}
