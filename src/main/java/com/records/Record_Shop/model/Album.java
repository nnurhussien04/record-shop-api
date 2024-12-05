package com.records.Record_Shop.model;

import jakarta.persistence.*;
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

    @Column
    String name;

    @Column
    String artist;

    @Column
    Integer album_year;

    @Column
    Integer price;

    @Column
    Integer stock;

    @Column
    Integer sales;

}
