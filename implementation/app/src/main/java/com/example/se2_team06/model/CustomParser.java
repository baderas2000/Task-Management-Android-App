package com.example.se2_team06.model;

abstract public class CustomParser {

    protected Object isNotNull(Object o) throws ParsingException {
        if (o == null) {
            throw new ParsingException(-1, "ParseException");
        }
        return o;
    }

}
