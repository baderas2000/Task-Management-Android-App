package com.example.se2_team06.model;

import org.json.simple.parser.ParseException;

/**
 * Class dedicated to adapt JSONParser to IParserAdapter interface.
 */

public class JSONParserAdapter implements IParserAdapter {

    final private JSONCustomParser adaptee = new JSONCustomParser();

    public TaskCollection importTasks (String string) throws ParsingException {
        return this.adaptee.importData(string);
    }

    public String exportTasks (TaskCollection tasks) throws ParsingException {
        return this.adaptee.exportData(tasks);
    }
    
}
