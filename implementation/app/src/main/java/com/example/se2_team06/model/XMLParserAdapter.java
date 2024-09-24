package com.example.se2_team06.model;

/**
 * Class dedicated to adapt XMLParser to IParserAdapter interface.
 */

public class XMLParserAdapter implements IParserAdapter {

    final private XMLCustomParser adaptee = new XMLCustomParser();

    public TaskCollection importTasks(String string) throws ParsingException {

        return this.adaptee.importData(string);
    }

    public String exportTasks (TaskCollection tasks) throws ParsingException {
        return this.adaptee.exportData(tasks);
    }

}
