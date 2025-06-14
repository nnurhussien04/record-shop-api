package com.records.Record_Shop.service;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.JSONObjectError;
import com.records.Record_Shop.exceptions.SQLError;
import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.model.Artist;
import com.records.Record_Shop.repository.AlbumRepository;
import com.records.Record_Shop.repository.ArtistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DataJpaTest
class AlbumServiceImplTest {

    @Mock
    AlbumRepository albumRepository;

    @Mock
    GenreRepository genreRepository;

    @Mock
    ArtistRepository artistRepository;

    @InjectMocks
    AlbumServiceImpl albumService;


    @Test
    @DisplayName("Testing if album returns ")
    void testlistAllAlbumsReturnAlbum() {
        List<Album> albums = new ArrayList<>();
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        albums.add(new Album().builder().album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build());
        albums.add(new Album().builder().album_name("Zero").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(0).build());

        List<Album> expectedResult = albums.stream().filter(x -> x.getStock() > 0).toList();

        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> actualResult = albumService.listAllInStock();

        assertThat(actualResult).hasSize(1);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Testing to see if album returns despite stock being 0")
    void testlistAllAlbumsDoesNotReturnAlbum() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album().builder().album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).sales(100).stock(0).build());

        when(albumRepository.findAll()).thenReturn(albums);

        assertThatThrownBy(() -> albumService.listAllInStock()).isInstanceOfAny(RuntimeException.class);

    }

    @Test()
    @DisplayName("Testing to see if album returns despite being null")
    void testlistAllAlbumsDoesNotReturnNull() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album().builder().build());

        when(albumRepository.findAll()).thenReturn(albums);

        assertThatThrownBy(() -> albumService.listAllInStock()).isInstanceOfAny(NullPointerException.class);

    }



    @Test
    @DisplayName("Testing to see if get album by ID returns an album that matches the listed ID")
    void testgetAlbumByIdReturnsAlbum() {
        Album album = new Album().builder().id(1l).album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).sales(100).stock(25).build();
        when(albumRepository.existsById(1l)).thenReturn(true);
        when(albumRepository.findById(1l)).thenReturn(Optional.ofNullable(album));
        Album actualResult = albumService.getAlbumById(1l).get();
        assertThat(actualResult).isInstanceOf(Album.class);
    }

    @Test
    @DisplayName("Testing to see if getAlbumByID does not return album that matches listed ID")
    void testgetAlbumByIdDoesNotReturnAlbum() {
        when(albumRepository.existsById(1l)).thenReturn(false);
        assertThatThrownBy(() -> albumService.getAlbumById(1l)).isInstanceOfAny(Invalid_ID.class);
    }

    @Test
    @DisplayName("Testing to see if addAlbum returns the album added")
    void testIfaddAlbumReturnsAlbum() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(1999).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(genreRepository.findByTitle("rap")).thenReturn(Optional.ofNullable(new Genre().builder().title("rap").build()));
        when(artistRepository.findByArtistName("JZ")).thenReturn(Optional.ofNullable(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()));
        when(albumRepository.save(album)).thenReturn(album);
        Album actualResult = albumService.addAlbum(album);
        assertThat(actualResult).isEqualTo(album);
        assertThat(actualResult).isInstanceOf(Album.class);
    }

    @Test
    @DisplayName("Testing to see if addAlbum does not return null value")
    void testIfaddAlbumReturnsNull(){
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name(null).album_year(2000).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        assertThatThrownBy(() -> albumService.addAlbum(album)).isInstanceOfAny(JSONObjectError.class);
    }
    @Test
    @DisplayName("Testing to see if addAlbum throws JSON Error if it has an invalid value")
    void testifaddAlbumReturnsPropertiesWithInvalidIntegerValues(){
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2026).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        assertThatThrownBy(() -> albumService.addAlbum(album)).isInstanceOfAny(JSONObjectError.class);
    }

    @Test
    @DisplayName("Testing to see updateAlbum will return album that is updated")
    void updateAlbumReturnsUpdatedAlbum() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(genreRepository.findByTitle("Rnb")).thenReturn(null);
        when(artistRepository.findByArtistName("JZ")).thenReturn(Optional.ofNullable(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()));
        when(albumRepository.existsById(1l)).thenReturn(true);
        when(albumRepository.save(album)).thenReturn(album);
        genres.add(new Genre().builder().title("RnB").build());
        album.setGenre(genres);
        assertThat(albumService.updateAlbum(album,1l).getGenre()).isEqualTo(album.getGenre());
    }

    @Test
    @DisplayName("Testing to see updateAlbum will return album if one property is null")
    void testIfupdateAlbumReturnsNull() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        Album album = new Album().builder().album_name(null).album_year(2000).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        assertThatThrownBy(() -> albumService.updateAlbum(album,1l)).isInstanceOfAny(JSONObjectError.class);
    }

    @Test
    @DisplayName("Testing to see if updateAlbum will return an album that doesn't exist in the database")
    void testIfupdateAlbumReturnsUpdatedAlbumWithInvalidID(){
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        Album album = new Album().builder().album_name(null).album_year(2000).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        album.setSales(90);
        when(albumRepository.existsById(1l)).thenReturn(false);
        assertThatThrownBy(() -> albumService.updateAlbum(album,1l)).isInstanceOf(Invalid_ID.class);
    }

    @Test
    @DisplayName("Testing to see if updateAlbum will return an updatedAlbum despite it not working")
    void updateAlbumReturnsAnAlbumThatFailedUpdate() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(genreRepository.findByTitle("Rnb")).thenReturn(null);
        when(artistRepository.findByArtistName("JZ")).thenReturn(Optional.ofNullable(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()));
        when(albumRepository.existsById(1l)).thenReturn(true);
        when(albumRepository.save(album)).thenReturn(null);
        assertThatThrownBy(() -> albumService.updateAlbum(album,1l)).isInstanceOf(SQLError.class);
    }

    @Test
    @DisplayName("Tesitng to see if deleteALbum will delete an album")
    void deleteAlbum() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(albumRepository.existsById(1l)).thenReturn(true);
        when(albumRepository.findById(1l)).thenReturn(Optional.ofNullable(album));
        doNothing().when(albumRepository).delete(album);
        assertThat(albumService.deleteAlbum(1l)).isEqualTo(true);
    }

    @Test
    @DisplayName("Tesitng to see if deleteALbum will delete an album")
    void testIfdeleteAlbumdeletesAlbumThatDoesNotExist() {
        Set<Genre> genres = new HashSet<>();
        genres.add(new Genre().builder().title("rap").build());
        genres.add(new Genre().builder().title("trap").build());
        Album album = new Album().builder().album_name("Unknown").album_year(2020).price(15).artist(new Artist().builder().artistName("JZ").birth_year(1990).hitSong("Riches").nationality("USA").build()).genre(genres).sales(100).stock(10).build();
        when(albumRepository.existsById(1l)).thenReturn(false);
        assertThatThrownBy(() -> albumService.deleteAlbum(1l)).isInstanceOf(Invalid_ID.class);

    }

}