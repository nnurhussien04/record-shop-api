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
@RequestMapping("api/v1/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> listAllAlbums(){
        List<Album> albums = albumService.listAllInStock();
        return new ResponseEntity<>(albums,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Album> getAlbumById(@RequestParam("id") long id){
        Album selectedAlbum = albumService.getAlbumById(id).get();
        return new ResponseEntity<>(selectedAlbum,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> addAlbum(@RequestBody Album album){
        Album newAlbum = albumService.addAlbum(album);
        return new ResponseEntity<>(newAlbum,HttpStatus.CREATED);
    }


    @PatchMapping({"update/{albumID}"})
    public ResponseEntity<Album> updateAlbum(@RequestBody Album album,@PathVariable long albumID){
        Album updatedAlbum = albumService.updateAlbum(album,albumID);
        return new ResponseEntity<>(updatedAlbum,HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAlbum(@RequestParam long id){
        Boolean deletedAlbum = albumService.deleteAlbum(id);
        if(deletedAlbum)
            return new ResponseEntity<>(deletedAlbum,HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(deletedAlbum,HttpStatus.EXPECTATION_FAILED);
    }




}
