package com.records.Record_Shop.service;

import com.records.Record_Shop.model.Album;

import java.util.ArrayList;

public interface AlbumService {
    ArrayList<Album> listAllInStock();
    Album getAlbumById(Long id);
    Album addAlbum(Album album);
    Album updateAlbum(Album album);
    Boolean deleteAlbum(Album album);

}
