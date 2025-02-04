package com.records.Record_Shop.repository;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.model.Artist;
import com.records.Record_Shop.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GenreRepositoryTest {
    @Autowired
    GenreRepository genreRepository;

    Genre genre;

    @BeforeEach
    void setup(){
        genre = new Genre().
                builder().
                title("rap").
                build();
        genreRepository.save(genre);
        //Album bob = repository.save(bobsAlbum);
        //bobsId = bob.getId();
    }

    @Test
    @DisplayName("Testing to see if findByID returns the genre with the ID listed")
    void findById() {
        var expectedResult = genre;
        var actualResult = genreRepository.findById(1l);
        assertEquals(expectedResult,actualResult.get());
    }

    @Test
    @DisplayName("Testing to see if findBYID returns an genre with an unknown ID ")
    void findById_UnknownID(){
        var actualResult = genreRepository.findById(3l);
        assertEquals(true,actualResult.isEmpty());
    }

    @Test
    @DisplayName("Testing to see if findByArtistName returns an artist with the listed name")
    void findByTitle() {
        var actualResult = genreRepository.findByTitle("rap");
        var expectedResult = genre;
        assertEquals(expectedResult,actualResult.get());
    }

    @Test
    @DisplayName("Testing to see if findByName returns an artist with an unknown name ")
    void findByArtistName_UnknownName(){
        var actualResult = genreRepository.findByTitle("Unknown");
        assertEquals(true,actualResult.isEmpty());
    }

    @Test
    @DisplayName("Testing to see if findByName returns an artist with an nullValue ")
    void findByArtistName_Null_Value(){
        var actualResult = genreRepository.findByTitle(null);
        assertEquals(true,actualResult.isEmpty());

    }
}