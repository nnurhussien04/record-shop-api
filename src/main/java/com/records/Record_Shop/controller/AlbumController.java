package com.records.Record_Shop.controller;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.SQLError;
import com.records.Record_Shop.model.Album;
import com.records.Record_Shop.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        List<Album> albums = new ArrayList<>();
        try {
            albums = albumService.listAllInStock();
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No albums is in stock", HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(albums,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAlbumById(@RequestParam("id") long id){
        Optional<Album> selectedAlbum = albumService.getAlbumById(id);;
        /*try {
            selectedAlbum = albumService.getAlbumById(id);
        } catch (Invalid_ID e){
            return new ResponseEntity<>("ID does not exist",HttpStatus.BAD_REQUEST);
        }*/
        return new ResponseEntity<>(selectedAlbum.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addAlbum(@RequestBody Album album){
        Album newAlbum = albumService.addAlbum(album);
        return new ResponseEntity<>(newAlbum,HttpStatus.CREATED);
    }


    @PutMapping({"update/{albumID}"})
    public ResponseEntity<?> updateAlbum(@RequestBody Album album,@PathVariable long albumID) throws Exception{
        Album updatedAlbum = null;
        try{
            updatedAlbum = albumService.updateAlbum(album,albumID);
        } catch (Invalid_ID e){
            return new ResponseEntity<>("ID not valid",HttpStatus.FORBIDDEN);
        } catch (NullPointerException e){
            return new ResponseEntity<>("Album listed is invalid",HttpStatus.FORBIDDEN);
        } catch (SQLError e){
            return new ResponseEntity<>("SQL System Error",HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(updatedAlbum,HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteAlbum(@RequestParam long id){
        Boolean deletedAlbum = null;
        try{
            deletedAlbum = albumService.deleteAlbum(id);
        } catch (Invalid_ID e){
            return new ResponseEntity<>(deletedAlbum,HttpStatus.FORBIDDEN);
        } catch (SQLError e){
            return new ResponseEntity<>(deletedAlbum,HttpStatus.EXPECTATION_FAILED);
        }
        if(deletedAlbum)
            return new ResponseEntity<>(deletedAlbum,HttpStatus.ACCEPTED);
        return new ResponseEntity<>(deletedAlbum,HttpStatus.EXPECTATION_FAILED);
    }

}
