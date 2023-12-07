package com.realestate.realestate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String icon;

    private Boolean built_in;

    private Integer seq;

    private String slug;

    private Boolean is_active;

    private LocalDate create_at;

    private LocalDate update_at;

    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<CategoryPropertyKey> categoryPropertyKeys;
}
