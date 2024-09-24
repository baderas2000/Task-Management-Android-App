package com.example.se2_team06.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_team06.R;
import com.example.se2_team06.model.EParseType;
import com.example.se2_team06.model.IOController;
import com.example.se2_team06.model.ParsingException;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class ImportExportActivity extends AppCompatActivity {

    private EditText filepath_view;
    private IOController ioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);
        this.ioController = new IOController(this);

        this.filepath_view = findViewById(R.id.filepath_edit_text);
        System.out.println("Created filepath_view");

        Button importJSONButton = findViewById(R.id.import_json_button);
        importJSONButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String pathToFile = filepath_view.getText().toString();
                    System.out.format("Import JSON file from location: %s\n", pathToFile);
                    ioController.importFromFile(pathToFile, EParseType.JSON);
                } catch (ParsingException e) {
                    System.out.println("Parsing Exception");
                } catch (NoSuchFileException e) {
                    System.out.println("No such file");
                }
            }
        });

        Button importXMLButton = findViewById(R.id.import_xml_button);
        importXMLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String pathToFile = filepath_view.getText().toString();
                    System.out.format("Import XML file from location: %s\n", pathToFile);
                    ioController.importFromFile(pathToFile, EParseType.XML);
                } catch (ParsingException e) {
                    System.out.println("Parsing Exception");
                } catch (NoSuchFileException e) {
                    System.out.println("No such file");
                }
            }
        });

        Button exportJSONButton = findViewById(R.id.export_json_button);
        exportJSONButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String pathToFile = filepath_view.getText().toString();
                    System.out.format("export JSON file from location: %s\n", pathToFile);
                    ioController.exportToFile(pathToFile, EParseType.JSON);
                } catch (ParsingException e) {
                    System.out.println("Parsing Exception");
                } catch (NoSuchFileException e) {
                    System.out.println("No such file");
                } catch (IOException e) {
                    System.out.println("IO exception raised.");
                }
            }
        });

        Button exportXMLButton = findViewById(R.id.export_xml_button);
        exportXMLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String pathToFile = filepath_view.getText().toString();
                    System.out.format("export XML file from location: %s\n", pathToFile);
                    ioController.exportToFile(pathToFile, EParseType.XML);
                } catch (ParsingException e) {
                    System.out.println("Parsing Exception");
                } catch (NoSuchFileException e) {
                    System.out.println("No such file");
                } catch (IOException e) {
                    System.out.println("IO exception raised.");
                }
            }
        });
    }



}
