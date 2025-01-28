package com.records.Record_Shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Table(name="genre")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Genre {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    @NotNull
    String title;



}
