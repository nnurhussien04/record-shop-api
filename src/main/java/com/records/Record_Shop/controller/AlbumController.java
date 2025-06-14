package com.records.Record_Shop.controller;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.SQLError;
import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<?> listAlbumsInStock(){
        List<Album> albums = albumService.listAllInStock();
        return new ResponseEntity<>(albums,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAlbumById(@RequestParam("id") long id){
        Optional<Album> selectedAlbum = albumService.getAlbumById(id);;
        return new ResponseEntity<>(selectedAlbum.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addAlbum(@Valid @RequestBody Album album){
        Album newAlbum = albumService.addAlbum(album);
        return new ResponseEntity<>(newAlbum,HttpStatus.CREATED);
    }


    @PutMapping({"update/{albumID}"})
    public ResponseEntity<?> updateAlbum(@Valid @RequestBody Album album,@PathVariable long albumID){
        Album updatedAlbum = albumService.updateAlbum(album,albumID);
        return new ResponseEntity<>(updatedAlbum,HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAlbum(@RequestParam long id){
        Boolean deletedAlbum = albumService.deleteAlbum(id);
        return new ResponseEntity<>(deletedAlbum,HttpStatus.ACCEPTED);
    }

    @GetMapping("/artist")
    public ResponseEntity<?> getArtistByName(@RequestParam("name") String name){
        Boolean selectedAlbum = albumService.findArtist(name);
        return new ResponseEntity<>(selectedAlbum,HttpStatus.OK);
    }

}
