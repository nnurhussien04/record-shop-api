package com.records.Record_Shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Album {
    @Id
    @GeneratedValue
    Long id;

    @Column
    String name;

    @Column
    String artist;

    @Column
    Integer year;

    @Column
    Integer price;

    @Column
    Integer stock;

    @Column
    Integer sales;




}
