package com.records.Record_Shop.service;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlbumServiceImpl implements  AlbumService{

    @Autowired
    AlbumRepository albumRepository;

    @Override
    public ArrayList<Album> listAllInStock() {
        return null;
    }

    @Override
    public Album getAlbumById(Long id) {
        return null;
    }

    @Override
    public Album addAlbum(Album album) {
        return null;
    }

    @Override
    public <T> Album updateAlbum(T property, Long id) {
        return null;
    }

    @Override
    public Boolean deleteAlbum(Long id) {
        return null;
    }
}
