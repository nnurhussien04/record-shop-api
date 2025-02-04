package com.records.Record_Shop.repository;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.model.Artist;
import com.records.Record_Shop.model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AlbumRepositoryTest {


    @Autowired
    AlbumRepository repository;


    Album bobsAlbum;

    @BeforeEach
    void setup(){
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        bobsAlbum = Album.builder()
                .id(1l)
                .album_name("Bob's album")
                .artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build())
                .price(5)
                .stock(10)
                .genre(genres)
                .build();
        //Album bob = repository.save(bobsAlbum);
        //bobsId = bob.getId();
    }


    @Test
    @DisplayName("Testing if save is working")
    void save() {
        var actualResult = repository.save(bobsAlbum);
        var expectedResult = bobsAlbum;
        assertEquals(expectedResult,actualResult);
    }

    @Test
    @DisplayName("Testing if save works with null values")
    void saveNull(){
        Album newAlbum = null;
        assertThrows(InvalidDataAccessApiUsageException.class,() -> repository.save(newAlbum));
    }

    @Test
    @DisplayName("Testing to see if findByID returns the album with the ID listed")
    void findById() {
        var expectedResult = bobsAlbum;
        var actualResult = repository.findById(1l);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    @DisplayName("Testing to see if findBYID returns an album with an unknown ID ")
    void findById_UnknownID(){
        var actualResult = repository.findById(2l);
        assertEquals(true,actualResult.isEmpty());
    }



    // Check to see if logs are repeated

}