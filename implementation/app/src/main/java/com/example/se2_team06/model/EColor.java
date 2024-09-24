package com.example.se2_team06.model;

public enum EColor {
    RED("RED"),
    BLACK("BLACK"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    GREY("GREY");

    private String friendlyName;

    EColor(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }
}
