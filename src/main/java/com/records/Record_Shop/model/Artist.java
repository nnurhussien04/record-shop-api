package com.records.Record_Shop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="artist")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Artist {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String artist_name;

    @Column
    int birth_year;

    @Column
    String nationality;

    @Column
    String hitSong;

}
