package com.records.Record_Shop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Column(name = "artist_name")
    @NotNull
    String artistName;

    @Column
    @NotNull
    int birth_year;

    @Column
    @NotNull
    String nationality;

    @Column
    @NotNull
    String hitSong;

}
