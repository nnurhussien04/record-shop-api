package com.records.Record_Shop.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;



@Entity
@Table(name="album")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Album {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @NotNull(message = "Name is empty")
    @Column
    String name;

    @NotNull(message = "Artist is empty")
    @Column
    String artist;

    @NotNull(message = "album_year is empty")
    @Column
    Integer album_year;

    @NotNull(message = "price is empty")
    @Column
    Integer price;

    @NotNull(message = "stock is empty")
    @Column
    Integer stock;

    @NotNull(message = "sales is empty")
    @Column
    Integer sales;

}
