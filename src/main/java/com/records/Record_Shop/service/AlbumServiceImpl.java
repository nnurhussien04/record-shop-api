package com.records.Record_Shop.service;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
            if(x.getStock() > 0){
                inStock.add(x);
            }
        });
        return inStock;
    }

    @Override
    public Optional<Album> getAlbumById(Long id) {
        Optional<Album> selectedALbum = albumRepository.findById(id);
        return selectedALbum;
    }

    @Override
    public Album addAlbum(Album album) {
        Album newAlbum = albumRepository.save(album);
        return newAlbum;
    }

    @Override
    public Album updateAlbum(Album album,Long id) {
        Album updatedAlbum = null;
        if(albumRepository.existsById(id)){
            album.setId(id);
            updatedAlbum = albumRepository.save(album);
        }
        return updatedAlbum;
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
