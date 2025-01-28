package com.records.Record_Shop.controller;

import com.records.Record_Shop.exceptions.Invalid_ID;
import com.records.Record_Shop.exceptions.SQLError;
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

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(){
        return new ResponseEntity<>("Unexpected error has come up, please try again", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(){
        return new ResponseEntity<>("Album listed is invalid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SQLError.class)
    public ResponseEntity<String> handleSQLError(){
        return new ResponseEntity<>("SQL System Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }






    


}
