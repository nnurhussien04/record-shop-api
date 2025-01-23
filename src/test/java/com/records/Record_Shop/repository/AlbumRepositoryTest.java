package com.records.Record_Shop.repository;

import com.records.Record_Shop.model.Album;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AlbumRepositoryTest {
    Long bobsId;

    @Autowired
    AlbumRepository repository;

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @BeforeEach
    void setup(){
        Album bobsAlbum = Album.builder()
                .name("Bob's album")
                .artist("Bob")
                .price(5)
                .stock(10)
                .build();

        Album bob = repository.save(bobsAlbum);
        bobsId = bob.getId();
    }

    // Check to see if logs are repeated
    @Test
    void saveLoadMultipleTimes(){
        Optional<Album> bob = null;
        for (int i = 0; i < 10; i++) {
            bob = repository.findById(bobsId);
        }

        System.out.println(bob.get());
        repository.save(bob.get());

        for (int i = 0; i < 10; i++) {
            bob = repository.findById(bobsId);
        }

    }
}