package com.records.Record_Shop.service;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
            try {
                if (x.getStock() > 0) {
                    inStock.add(x);
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        });
        return inStock;
    }

    @Override
    public Optional<Album> getAlbumById(Long id) throws Exception{
        Optional<Album> selectedAlbum = albumRepository.findById(id);
        if(selectedAlbum.isEmpty()){
            throw new Exception();
        }
        return selectedAlbum;
    }

    @Override
    public Album addAlbum(Album album) {
        Album newAlbum = null;
        try{
            newAlbum = albumRepository.save(album);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return newAlbum;
    }

    @Override
    public Album updateAlbum(Album album,Long id) throws Exception{
        Album updatedAlbum = null;
        if(albumRepository.existsById(id)){
            album.setId(id);
            updatedAlbum = albumRepository.save(album);
            return updatedAlbum;
        } else{
            throw new Exception();
        }

    }

    @Override
    public Boolean deleteAlbum(Long id) {
        try {
            albumRepository.delete(albumRepository.findById(id).get());
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
