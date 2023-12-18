package com.realestate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category_property_keys")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryPropertyKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    private Boolean builtIn = false;


    @ManyToOne
    private Category category;


    @OneToMany(mappedBy = "categoryPropertyKey", cascade = CascadeType.REMOVE)
    private List<CategoryPropertyValue> categoryPropertyValue;

}
