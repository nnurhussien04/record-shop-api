package com.records.Record_Shop.controller;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.JSONObjectError;
import com.records.Record_Shop.exceptions.SQLError;
import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.model.Artist;
import com.records.Record_Shop.service.AlbumServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DataJpaTest
class AlbumControllerTest {

    @Mock
    AlbumServiceImpl albumService;

    @InjectMocks
    AlbumController albumController;

    @InjectMocks
    AlbumControllerAdvice albumControllerAdvice;


    @Test
    @DisplayName("Testing if album returns ")
    void testlistAllAlbumsReturnAlbum() {
        List<Album> albums = new ArrayList<>();
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        albums.add(new Album().builder().album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build());
        albums.add(new Album().builder().album_name("Zero").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(0).build());

        ResponseEntity<?> expectedResult = new ResponseEntity<>(albums, HttpStatus.OK);
        when(albumService.listAllInStock()).thenReturn((ArrayList<Album>) albums);

        var actualResult = albumController.listAlbumsInStock();

        assertEquals(expectedResult.getStatusCode(),actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(),actualResult.getBody());

    }

    @Test
    @DisplayName("Testing to see if album returns despite stock being 0")
    void testlistAllAlbumsDoesNotReturnAlbum() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album().builder().album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).sales(100).stock(0).build());

        ResponseEntity<?> expectedResult = new ResponseEntity<>("Unexpected error has come up, please try again", HttpStatus.INTERNAL_SERVER_ERROR);

        var actualResult = albumController.listAlbumsInStock();
        when(albumService.listAllInStock()).thenThrow(RuntimeException.class);
        try {
            albumController.listAlbumsInStock();
        } catch (RuntimeException e){
            var response = albumControllerAdvice.handleRuntimeException();
            assertEquals(expectedResult.getStatusCode(),response.getStatusCode());
            assertEquals(expectedResult.getBody(),response.getBody());
        }

    }

    @Test()
    @DisplayName("Testing to see if album returns despite being null")
    void testlistAllAlbumsDoesNotReturnNull() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album().builder().build());

        ResponseEntity<?> expectedResult = new ResponseEntity<>("Unexpected error has come up, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        var actualResult = albumController.listAlbumsInStock();
        when(albumService.listAllInStock()).thenThrow(RuntimeException.class);
        try {
            actualResult = albumController.listAlbumsInStock();
        } catch (RuntimeException e){
            var response = albumControllerAdvice.handleRuntimeException();
            assertEquals(expectedResult.getStatusCode(),response.getStatusCode());
            assertEquals(expectedResult.getBody(),response.getBody());
        }
    }



    @Test
    @DisplayName("Testing to see if get album by ID returns an album that matches the listed ID")
    void testgetAlbumByIdReturnsAlbum() {
        Album album = new Album().builder().id(1l).album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).sales(100).stock(25).build();
        when(albumService.getAlbumById(1l)).thenReturn(Optional.ofNullable(album));
        ResponseEntity<?> expectedResult = new ResponseEntity<>(album, HttpStatus.OK);
        var actualResult = albumController.getAlbumById(1l);

        assertEquals(expectedResult.getStatusCode(),actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(),actualResult.getBody());

    }

    @Test
    @DisplayName("Testing to see if getAlbumByID does not return album that matches listed ID")
    void testgetAlbumByIdDoesNotReturnAlbum() {
        when(albumService.getAlbumById(1l)).thenThrow(Invalid_ID.class);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("ID does not exist", HttpStatus.BAD_REQUEST);
        try {
             var actualResult = albumController.listAlbumsInStock();
        } catch (RuntimeException e){
            var response = albumControllerAdvice.handleInvalidID();
            assertEquals(expectedResult.getStatusCode(),response.getStatusCode());
            assertEquals(expectedResult.getBody(),response.getBody());
        }
        //assertThatThrownBy(() -> albumService.getAlbumById(1l)).isInstanceOfAny(Invalid_ID.class);
    }

    @Test
    @DisplayName("Testing to see if addAlbum returns the album added")
    void testIfaddAlbumReturnsAlbum() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(albumService.addAlbum(album)).thenReturn(album);
        ResponseEntity<?> expectedResult =  new ResponseEntity<>(album,HttpStatus.CREATED);
        var actualResult = albumController.addAlbum(album);
        assertEquals(expectedResult.getStatusCode(),actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(),actualResult.getBody());
    }

    @Test
    @DisplayName("Testing to see if addAlbum does not return null value")
    void testIfaddAlbumReturnsNull(){
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name(null).album_year(2000).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        //assertThatThrownBy(() -> albumService.addAlbum(album)).isInstanceOfAny(JSONObjectError.class);
        when(albumService.addAlbum(album)).thenThrow(JSONObjectError.class);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("An error with the album you have submitted", HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            var actualResult = albumController.addAlbum(album);
        } catch(JSONObjectError e) {
            var response = albumControllerAdvice.handleJSONObjectError();
            assertEquals(expectedResult.getStatusCode(), response.getStatusCode());
            assertEquals(expectedResult.getBody(), response.getBody());
        }
    }
    @Test
    @DisplayName("Testing to see if addAlbum throws JSON Error if it has an invalid value")
    void testifaddAlbumReturnsPropertiesWithInvalidIntegerValues(){
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2026).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        //assertThatThrownBy(() -> albumService.addAlbum(album)).isInstanceOfAny(JSONObjectError.class);
        when(albumService.addAlbum(album)).thenThrow(JSONObjectError.class);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("An error with the album you have submitted", HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            var actualResult = albumController.addAlbum(album);
        } catch(JSONObjectError e) {
            var response = albumControllerAdvice.handleJSONObjectError();
            assertEquals(expectedResult.getStatusCode(), response.getStatusCode());
            assertEquals(expectedResult.getBody(), response.getBody());
        }
    }

    @Test
    @DisplayName("Testing to see updateAlbum will return album that is updated")
    void updateAlbumReturnsUpdatedAlbum() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().id(1l).album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        genres.add(new Genre().builder().title("RnB").build());
        album.setGenre(genres);
        when(albumService.updateAlbum(album,1l)).thenReturn(album);
        ResponseEntity<?> expectedResult =  new ResponseEntity<>(album,HttpStatus.ACCEPTED);
        var actualResult = albumController.updateAlbum(album,1l);
        assertEquals(expectedResult.getStatusCode(),actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(),actualResult.getBody());

    }

    @Test
    @DisplayName("Testing to see updateAlbum will return album if one property is null")
    void testIfupdateAlbumReturnsNull() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        Album album = new Album().builder().id(1l).album_name(null).album_year(2000).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(albumService.updateAlbum(album,1l)).thenThrow(JSONObjectError.class);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("An error with the album you have submitted", HttpStatus.INTERNAL_SERVER_ERROR);
        ;
        try{
            var actualResult = albumController.updateAlbum(album,1l);
        } catch (JSONObjectError e){
            var response = albumControllerAdvice.handleJSONObjectError();
            assertEquals(response.getStatusCode(),expectedResult.getStatusCode());
            assertEquals(response.getBody(),expectedResult.getBody());
        }

        //assertThatThrownBy(() -> albumService.updateAlbum(album,1l)).isInstanceOfAny(JSONObjectError.class);
    }

    @Test
    @DisplayName("Testing to see if updateAlbum will return an album that doesn't exist in the database")
    void testIfupdateAlbumReturnsUpdatedAlbumWithInvalidID(){
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        Album album = new Album().builder().album_name(null).album_year(2000).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        album.setSales(90);
        when(albumService.updateAlbum(album,1l)).thenThrow(Invalid_ID.class);
        ResponseEntity<?> expectedResult = new ResponseEntity<>("ID does not exist", HttpStatus.BAD_REQUEST);
        try{
            var actualResult = albumController.updateAlbum(album,1l);
        } catch (Invalid_ID e){
            var response = albumControllerAdvice.handleInvalidID();
            assertEquals(expectedResult.getStatusCode(),response.getStatusCode());
            assertEquals(expectedResult.getBody(),response.getBody());
        }
    }

    @Test
    @DisplayName("Testing to see if updateAlbum will return an updatedAlbum despite it not working")
    void updateAlbumReturnsAnAlbumThatFailedUpdate() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(albumController.updateAlbum(album,1l)).thenThrow(SQLError.class);
        var expectedResult =  new ResponseEntity<>("SQL System Error", HttpStatus.INTERNAL_SERVER_ERROR);
        try{
            var actualResult = albumController.updateAlbum(album,1l);
        } catch (SQLError e){
            var response = albumControllerAdvice.handleSQLError();
            assertEquals(expectedResult.getStatusCode(),response.getStatusCode());
            assertEquals(expectedResult.getBody(),response.getBody());
        }
        //assertThatThrownBy(() -> albumService.updateAlbum(album,1l)).isInstanceOf(SQLError.class);
    }

    @Test
    @DisplayName("Tesitng to see if deleteALbum will delete an album")
    void deleteAlbum() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().id(1l).album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(albumService.deleteAlbum(1l)).thenReturn(true);
        //assertThat(albumService.deleteAlbum(1l)).isEqualTo(true);
        var expectedResult = new ResponseEntity<>(true,HttpStatus.ACCEPTED);
        var actualResult = albumController.deleteAlbum(1l);
        assertEquals(expectedResult.getStatusCode(),actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(),actualResult.getBody());
    }

    @Test
    @DisplayName("Tesitng to see if deleteALbum will delete an album")
    void testIfdeleteAlbumdeletesAlbumThatDoesNotExist() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(albumController.deleteAlbum(1l)).thenThrow(Invalid_ID.class);
        //assertThatThrownBy(() -> albumService.deleteAlbum(1l)).isInstanceOf(Invalid_ID.class);
        var expectedResult = new ResponseEntity<>("ID does not exist", HttpStatus.BAD_REQUEST);
        try{
            var actualResult = albumController.deleteAlbum(1l);
        } catch (Invalid_ID e){
            var response = albumControllerAdvice.handleInvalidID();
            assertEquals(expectedResult.getStatusCode(),response.getStatusCode());
            assertEquals(expectedResult.getBody(),response.getBody());
        }

    }

}