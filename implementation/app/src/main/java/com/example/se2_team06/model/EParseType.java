package com.example.se2_team06.model;

import androidx.annotation.NonNull;

public enum EParseType {
    JSON("JSON"),
    XML("XML");

    final private String friendlyName;

    EParseType(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @NonNull
    @Override
    public String toString(){
        return friendlyName;
    }
}
