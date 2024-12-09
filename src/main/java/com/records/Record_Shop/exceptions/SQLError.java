package com.records.Record_Shop.exceptions;

public class SQLError extends RuntimeException{

    public SQLError() {
    }

    public SQLError(String message) {
        super(message);
    }
}
