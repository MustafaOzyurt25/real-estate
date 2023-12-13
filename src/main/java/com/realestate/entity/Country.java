package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "country",cascade = CascadeType.REMOVE)
    private List<City> cities;
    @JsonIgnore
    @OneToMany(mappedBy = "country",cascade = CascadeType.REMOVE)
    private List<Advert> advert;


}
