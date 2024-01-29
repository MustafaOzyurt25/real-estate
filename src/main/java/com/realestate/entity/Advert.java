package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.realestate.entity.enums.AdvertStatus;
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
public class Advert {  // 23 tane field..vs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(unique = true)
    private String slug;

    private Double price;

    @Enumerated(EnumType.ORDINAL)
    private AdvertStatus status;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean builtIn;

    private Boolean isActive;

    private Integer viewCount;
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.REMOVE)
    private List<CategoryPropertyValue> categoryPropertyValue;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "advert", cascade = CascadeType.REMOVE)
    private List<TourRequest> tourRequests;

    @OneToMany(cascade = CascadeType.REMOVE , fetch = FetchType.EAGER)
    @JoinColumn(name = "advert_id")
    private List<Image> images;

    @OneToMany(mappedBy = "advert",cascade = CascadeType.REMOVE)
    private List<LogAdvert> logAdverts;

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
