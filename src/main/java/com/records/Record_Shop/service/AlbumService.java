package com.records.Record_Shop.service;

import com.records.Record_Shop.model.Album;

import java.util.ArrayList;
import java.util.Optional;

public interface AlbumService {
    ArrayList<Album> listAllInStock();
    Optional<Album> getAlbumById(Long id) throws Exception;
    Album addAlbum(Album album);
    Album updateAlbum(Album album, Long id) throws Exception;
    Boolean deleteAlbum(Long id);

}
