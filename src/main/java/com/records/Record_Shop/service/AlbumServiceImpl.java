package com.records.Record_Shop.service;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.JSONObjectError;
import com.records.Record_Shop.exceptions.SQLError;
import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.model.Artist;
import com.records.Record_Shop.repository.AlbumRepository;
import com.records.Record_Shop.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements  AlbumService{

    @Autowired
    AlbumRepository albumRepository;

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
        if(album.getAlbum_name() == null || (album.getAlbum_year() < 1948 || album.getAlbum_year() > 2025 ) || album.getArtist() == null ||  album.getStock() < 0 || album.getPrice() < 0 || album.getSales() < 0){
            throw new JSONObjectError();
        }

        Artist artist = artistRepository.findByArtistName(album.getArtist().getArtistName()).orElseGet(() -> artistRepository.save(album.getArtist()));
        album.setArtist(artist);

        newAlbum = albumRepository.save(album);
        return newAlbum;
    }

    @Override
    public Album updateAlbum(Album album,Long id) {
        Album updatedAlbum = null;
        if(album.getAlbum_name() == null || (album.getAlbum_year() < 1948 || album.getAlbum_year() > 2025 ) || album.getArtist() == null || album.getStock() < 0 || album.getPrice() < 0 || album.getSales() < 0){
            throw new JSONObjectError();
        }
        if(albumRepository.existsById(id)){
            album.setId(id);

            Artist inputArtist = album.getArtist();

            Artist artist = Optional.ofNullable(inputArtist)
                    .flatMap(a -> artistRepository.findByArtistName(a.getArtistName()))
                    .orElseGet(() -> artistRepository.save(inputArtist));


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
        return true;
    }

    @Override
    public Boolean findArtist(String inputArtist){
        return artistRepository.findByArtistName(inputArtist)
                .isPresent();
    }
}
