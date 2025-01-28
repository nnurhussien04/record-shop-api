package com.records.Record_Shop.service;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.SQLError;
import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.model.Artist;
import com.records.Record_Shop.model.Genre;
import com.records.Record_Shop.repository.AlbumRepository;
import com.records.Record_Shop.repository.GenreRepository;
import com.records.Record_Shop.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements  AlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Override
    public ArrayList<Album> listAllInStock() {
        ArrayList<Album> inStock = new ArrayList<>();
        albumRepository.findAll().forEach(x -> {
            if (x.getStock() > 0) {
                inStock.add(x);
            }
        });
        if(inStock.isEmpty()) {
            throw new RuntimeException();
        }
        return inStock;
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        if(!albumRepository.existsById(id)){
            throw new Invalid_ID();
        }
        Optional<Album> selectedAlbum = albumRepository.findById(id);
        if(selectedAlbum.isEmpty()){
            throw new SQLError();
        }
        return selectedAlbum;
    }

    @Override
    public Album addAlbum(Album album) {
        Album newAlbum = null;

        Set<Genre> genres = album.getGenre().stream().map(
                genre -> genreRepository.findByTitle(genre.getTitle()).orElseGet(() -> genreRepository.save(genre))).collect(Collectors.toSet());
        album.setGenre(genres);

        Artist artist = artistRepository.findByArtistName(album.getArtist().getArtistName()).orElseGet(() -> artistRepository.save(album.getArtist()));
        album.setArtist(artist);

        newAlbum = albumRepository.save(album);
        return newAlbum;
    }

    @Override
    public Album updateAlbum(Album album,Long id) {
        Album updatedAlbum = null;
        if(album.equals(null)){
            throw new NullPointerException();
        }
        if(albumRepository.existsById(id)){
            album.setId(id);
            Set<Genre> genres = album.getGenre().stream().map(
                    genre -> genreRepository.findByTitle(genre.getTitle()).orElseGet(() -> genreRepository.save(genre))).collect(Collectors.toSet());
            album.setGenre(genres);

            Artist artist = artistRepository.findByArtistName(album.getArtist().getArtistName()).orElseGet(() -> artistRepository.save(album.getArtist()));
            album.setArtist(artist);

            updatedAlbum = albumRepository.save(album);
        } else {
            throw new Invalid_ID();
        }
        if(updatedAlbum == null){
            throw new SQLError();
        }
        return updatedAlbum;
    }

    @Override
    public Boolean deleteAlbum(Long id) {
        if(!albumRepository.existsById(id)){
            throw new Invalid_ID();
        }
        albumRepository.delete(albumRepository.findById(id).get());
        if(albumRepository.existsById(id)){
            throw new SQLError();
        }
        return true;
    }
}
