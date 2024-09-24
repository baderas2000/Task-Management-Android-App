package com.example.se2_team06.model;

import org.json.simple.parser.ParseException;

/**
 * Single parser adapter interface.
 * Interface implemented by different parser adapter.
 */
 public interface IParserAdapter {

    public TaskCollection importTasks (String string) throws ParsingException;
    public String exportTasks (TaskCollection tasks) throws ParsingException ;

}