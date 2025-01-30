package com.records.Record_Shop.repository;

import com.records.Record_Shop.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {


    Optional<Genre> findById(Long aLong);

    Optional<Genre> findByTitle(String title);

}
