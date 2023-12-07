package com.realestate.realestate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "districts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "district",cascade = CascadeType.REMOVE)
    private List<Advert> advert;
}
