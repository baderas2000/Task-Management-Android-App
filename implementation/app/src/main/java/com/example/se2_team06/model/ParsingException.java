package com.example.se2_team06.model;

public class ParsingException extends Exception{

    private int id;
    private String message;

    ParsingException(int id, String message) {
        this.id = id;
        this.message = message;
    }

}
