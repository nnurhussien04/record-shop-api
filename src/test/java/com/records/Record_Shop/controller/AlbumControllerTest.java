package com.records.Record_Shop.controller;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.repository.AlbumRepository;
import com.records.Record_Shop.service.AlbumService;
import com.records.Record_Shop.service.AlbumServiceImpl;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
class AlbumControllerTest {

    @Mock
    AlbumRepository albumRepository;

    @InjectMocks
    AlbumServiceImpl albumService;


    @Test
    @DisplayName("Testing if album returns ")
    void testlistAllAlbumsReturnAlbum() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album().builder().name("Unknown").album_year(1999).price(15).artist("JZ").sales(100).stock(10).build());
        albums.add(new Album().builder().name("Zero").album_year(1999).price(15).artist("JZ").sales(100).stock(0).build());

        List<Album> expectedResult = albums.stream().filter(x -> x.getStock() > 0).toList();


        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = albumService.listAllInStock();

        assertThat(actualResult).hasSize(1);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    void testlistAllAlbumsDoesNotReturnAlbum() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album().builder().name("Unknown").album_year(1999).price(15).artist("JZ").sales(100).stock(0).build());

        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = albumService.listAllInStock();

        assertThat(actualResult).hasSize(0);
        assertThat(actualResult).isNotEqualTo(albums);
    }

    @Test
    void testlistAllAlbumsDoesNotReturnNull() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album().builder().build());

        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = albumService.listAllInStock();

        assertThat(actualResult).hasSize(0);
        assertThat(actualResult).isNotEqualTo(albums);
    }



    @Test
    void getAlbumById() {

    }

    @Test
    void addAlbum() {
    }

    @Test
    void updateAlbum() {
    }

    @Test
    void deleteAlbum() {
    }
}