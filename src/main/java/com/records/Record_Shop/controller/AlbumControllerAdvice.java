package com.records.Record_Shop.controller;

import com.records.Record_Shop.exceptions.Invalid_ID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AlbumControllerAdvice {

    @ExceptionHandler(value = Invalid_ID.class)
    public ResponseEntity<String> handleInvalidID(){
        return new ResponseEntity<>("ID does not exist", HttpStatus.BAD_REQUEST);
    }
}
