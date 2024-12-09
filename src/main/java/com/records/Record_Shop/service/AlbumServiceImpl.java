package com.records.Record_Shop.service;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.SQLError;
import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements  AlbumService{

    @Autowired
    AlbumRepository albumRepository;

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
        if(id < 0){
            throw new IllegalArgumentException();
        }
        if(!albumRepository.existsById(id)){
            throw new Invalid_ID();
        }
        Optional<Album> selectedAlbum = albumRepository.findById(id);
        return selectedAlbum;
    }

    @Override
    public Album addAlbum(Album album) {
        Album newAlbum = null;
        if(album == null){
            throw new NullPointerException();
        }
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
