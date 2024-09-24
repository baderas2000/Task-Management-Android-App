package com.example.se2_team06.model;

public enum EStatus {
    PLANNED("PLANNED"),
    ACTIVE("ACTIVE"),
    CANCELLED("CANCELLED"),
    DONE("DONE");

    private String friendlyName;

    EStatus(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }
}
