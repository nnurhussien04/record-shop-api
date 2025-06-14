package com.records.Record_Shop.repository;

import com.records.Record_Shop.model.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArtistRepositoryTest {

    Artist Bob;

    @Autowired
    ArtistRepository artistRepository;

    @BeforeEach
    void setup(){
        Bob = new Artist().
                builder().
                artistName("JZ").
                birth_year(1990).
                hitSong("Riches").
                nationality("USA").
                build();
        artistRepository.save(Bob);
    }

    @Test
    @DisplayName("Testing to see if findByID returns the artist with the ID listed")
    void findById() {
        var expectedResult = Bob;
        var actualResult = artistRepository.findById(1l);
        assertEquals(expectedResult,actualResult.get());
    }

    @Test
    @DisplayName("Testing to see if findBYID returns an artist with an unknown ID ")
    void findById_UnknownID(){
        var actualResult = artistRepository.findById(2l);
        assertEquals(true,actualResult.isEmpty());
    }

    @Test
    @DisplayName("Testing to see if findByArtistName returns an artist with the listed name")
    void findByArtistName() {
        var actualResult = artistRepository.findByArtistName("JZ");
        var expectedResult = Bob;
        assertEquals(expectedResult,actualResult.get());
    }

    @Test
    @DisplayName("Testing to see if findByName returns an artist with an unknown name ")
    void findByArtistName_UnknownName(){
        var actualResult = artistRepository.findByArtistName("Unknown");
        assertEquals(true,actualResult.isEmpty());
    }

    @Test
    @DisplayName("Testing to see if findByName returns an artist with an nullValue ")
    void findByArtistName_Null_Value(){
        var actualResult = artistRepository.findByArtistName(null);
        assertEquals(true,actualResult.isEmpty());

    }





}