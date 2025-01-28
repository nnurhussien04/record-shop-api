package com.records.Record_Shop.repository;

import com.records.Record_Shop.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Long>{


    Optional<Artist> findById(Long aLong);

    Optional<Artist> findByArtistName(String string);

}
