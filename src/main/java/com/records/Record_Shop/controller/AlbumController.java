package com.records.Record_Shop.controller;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("album/v1")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> listAllAlbums(){
        List<Album> albums = albumService.listAllInStock();
        return new ResponseEntity<>(albums,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Album> getAlbumById(@RequestParam("id") long id){
        Album selectedAlbum = albumService.getAlbumById(id);
        return new ResponseEntity<>(selectedAlbum,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> addAlbum(@RequestBody Album album){
        Album newAlbum = albumService.addAlbum(album);
        return new ResponseEntity<>(newAlbum,HttpStatus.CREATED);
    }

    @PatchMapping({"update/{albumID}"})
    public ResponseEntity<Album> updateAlbum(@RequestBody Object property,@PathVariable Long id){
        Album updatedAlbum = albumService.updateAlbum(property,id);
        return new ResponseEntity<>(updatedAlbum,HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAlbum(@RequestParam Long id){
        Boolean deletedAlbum = albumService.deleteAlbum(id);
        return new ResponseEntity<>(deletedAlbum,HttpStatus.ACCEPTED);
    }




}
