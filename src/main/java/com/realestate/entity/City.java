package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Country country;
    @JsonIgnore
    @OneToMany(mappedBy = "city",cascade = CascadeType.REMOVE)
    private List<District> districts;

    @JsonIgnore
    @OneToMany(mappedBy = "city",cascade = CascadeType.REMOVE)
    private List<Advert> advert;


}
