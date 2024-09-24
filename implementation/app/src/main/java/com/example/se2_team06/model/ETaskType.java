package com.example.se2_team06.model;

public enum ETaskType {
    PRIVATE("PRIVATE"),
    BUSINESS("BUSINESS");

    private String friendlyName;

    ETaskType(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }
}
