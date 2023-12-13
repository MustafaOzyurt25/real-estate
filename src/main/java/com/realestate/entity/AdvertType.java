package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "advert_types" )
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AdvertType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "advertType",cascade = CascadeType.REMOVE)
    private List<Advert> adverts;

}
