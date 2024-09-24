package com.example.se2_team06.model;

import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_team06.viewmodel.TaskViewModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Provides functionality to import and export Task.
 * Task will be import to the database from file or
 * exported from the database to file.
 */

public class IOController {

    final private AppCompatActivity activity;
    final private TaskViewModel database;
    final private JSONParserAdapter JSONParser = new JSONParserAdapter();
    final private XMLParserAdapter XMLParser = new XMLParserAdapter();
    private IParserAdapter parser;


    private void updateTasks (TaskCollection tasks) {
        for (Iterator<Task> i = tasks.getIterator(); i.hasNext();) {
            this.database.insert(i.next());
            //this.database.update(i.next());
        }
    }

    private TaskCollection getTasks () {
        TaskCollection result = new TaskCollection();
        // Returns empty list, however database contains tasks.
        List<Task> tasks = this.database.getAllTasks().getValue();
        if (tasks == null) {
            System.out.println("List<Task> is null.");
            return result;
        }
        for (Task task: tasks) {
            System.out.println("Add new task.");
            result.addTask(task);
        }
        return result;
    }

    public IOController(AppCompatActivity activity) {
        this.activity = activity;
        this.database = new ViewModelProvider(activity).get(TaskViewModel.class);
    }

    public void setParser (EParseType type) {
        if (type == EParseType.JSON) {
            this.parser = this.JSONParser;
        } else if (type == EParseType.XML) {
            this.parser = this.XMLParser;
        }
    }

    private String readFile (String assetName) throws ParsingException{
        String string;
        try {
            InputStream inputStream = this.activity.getAssets().open(assetName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
            return string;
        } catch (IOException e) {
            throw new ParsingException(1, "No such file.");
        }
    }

    /**
     * Import tasks from file with filename 'string' to the database.
     * @param fileName Name of file, that contains serialized tasks.
     * @return True if operation is successful else false.
     */
    public boolean importFromFile(String fileName, EParseType type) throws NoSuchFileException, ParsingException {
        System.out.format("Invoke importFromFile(%s, %s)\n", fileName, type.toString());
        String string = this.readFile(fileName);
        this.setParser(type);
        TaskCollection tasks = this.parser.importTasks(string);
        System.out.println(string);
        System.out.println(tasks.toString());
        this.updateTasks(tasks);
        return true;
    }

    /**
     * Export tasks to file with filename 'string' from the database.
     * @param fileName Name of file, that will contains serialized tasks.
     * @return True if operation is successful else false.
     */
    public boolean exportToFile(String fileName, EParseType type)
            throws IOException, ParsingException {
        System.out.println("Tasks saved in DataBase:");
        this.setParser(type);
        String tasksToExport = this.parser.exportTasks(this.getTasks());
        File file = new File(this.activity.getExternalFilesDir(null), fileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(tasksToExport);
        fileWriter.close();
        return true;
    }

}
