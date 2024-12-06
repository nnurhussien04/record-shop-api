package com.records.Record_Shop.controller;

import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<Album> albums = new ArrayList<>();
        albums = albumService.listAllInStock();
        if(albums.isEmpty() || albums == null)
            return new ResponseEntity<>("No albums is in stock",HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>(albums,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAlbumById(@RequestParam("id") long id) throws Exception{
        Optional<Album> selectedAlbum = albumService.getAlbumById(id);
        if(selectedAlbum.isEmpty()){
            return new ResponseEntity<>("ID not found",HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(selectedAlbum.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addAlbum(@RequestBody Album album){
        Album newAlbum = albumService.addAlbum(album);
        return new ResponseEntity<>(newAlbum,HttpStatus.CREATED);
    }


    @PatchMapping({"update/{albumID}"})
    public ResponseEntity<?> updateAlbum(@RequestBody Album album,@PathVariable long albumID) throws Exception{
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
