package com.realestate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="categories")
@Builder(toBuilder = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String icon;

    private Boolean built_in=false;

    private Integer seq;

    private String slug;

    private Boolean is_active;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime create_at=LocalDateTime.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime update_at;

    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<CategoryPropertyKey> categoryPropertyKeys;

    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    private List<Advert> adverts;
}
