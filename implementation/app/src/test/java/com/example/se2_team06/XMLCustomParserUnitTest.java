package com.example.se2_team06;

import static org.junit.Assert.assertEquals;

import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Checklist;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.JSONCustomParser;
import com.example.se2_team06.model.Task;
import com.example.se2_team06.model.Time;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.ETaskType;
import com.example.se2_team06.model.ParsingException;
import com.example.se2_team06.model.TaskCollection;
import com.example.se2_team06.model.XMLCustomParser;

import org.junit.Test;
import org.junit.experimental.theories.ParameterSignature;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class XMLCustomParserUnitTest {

    @Test
    public void importAppointmentAndChecklist() {

        TaskCollection tasks_expected = new TaskCollection();
        TaskCollection tasks_actual = new TaskCollection();
        XMLCustomParser parser = new XMLCustomParser();
        String xml_string;

        try {
            String filePath = ".\\example\\xml_test_1";
            List<String> array = Files.readAllLines(Paths.get(filePath));
            xml_string = String.join("\n", array);
        } catch (IOException e) {
            xml_string = "";
        }
        //System.out.println(xml_string);
        Appointment appointment = new Appointment(
                "Simple title",
                "Simple description",
                EColor.BLUE,
                EStatus.PLANNED,
                EPriority.LOW,
                "Appointment",
                "Simple address",
                new Date(5, 12, 2023),
                ETaskType.BUSINESS
        );
        Checklist checklist = new Checklist(
                "Complicated title",
                "Not simple description",
                EColor.GREEN,
                EStatus.DONE,
                EPriority.HIGH,
                "Appointment",
                new Date(1, 1, 2019),
                ETaskType.PRIVATE,
                new Time(12,15)
        );

        tasks_expected.addTask(appointment);
        tasks_expected.addTask(checklist);

        try {
            tasks_actual = parser.importData(xml_string);
        } catch (ParsingException e) {
            e.printStackTrace();
            System.out.println("Parsing is unsuccessful");
        }

        assertEquals(tasks_expected, tasks_actual);

    }

    @Test
    public void exportAppointmentAndChecklist() {
        String xml_string;
        TaskCollection tasks = new TaskCollection();
        XMLCustomParser parser = new XMLCustomParser();

        Appointment appointment = new Appointment(
                "Simple title",
                "Simple description",
                EColor.BLUE,
                EStatus.PLANNED,
                EPriority.LOW,
                "Appointment",
                "Simple address",
                new Date(5, 12, 2023),
                ETaskType.BUSINESS
        );
        Checklist checklist = new Checklist(
                "Complicated title",
                "Not simple description",
                EColor.GREEN,
                EStatus.DONE,
                EPriority.HIGH,
                "Checklist",
                new Date(1, 1, 2019),
                ETaskType.PRIVATE,
                new Time(12,15)
        );

        tasks.addTask(appointment);
        tasks.addTask(checklist);

        String tasks_expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><tasks><task class=\"appointment\"><title>Simple title</title><description>Simple description</description><color>BLUE</color><status>PLANNED</status><priority>LOW</priority><location>Simple address</location><type>BUSINESS</type><date><day>5</day><month>12</month><year>2023</year></date></task><task class=\"checklist\"><title>Complicated title</title><description>Not simple description</description><color>GREEN</color><status>DONE</status><priority>HIGH</priority><type>PRIVATE</type><deadline><day>1</day><month>1</month><year>2019</year></deadline><time><minute>15</minute><hour>12</hour></time></task></tasks>";
        String tasks_actual = "";

        try {
            tasks_actual = parser.exportData(tasks);

        } catch (ParsingException e) {
            e.printStackTrace();
            System.out.println("Exporting is unsuccessful.");
        }
        assertEquals(tasks_expected, tasks_actual);
    }

    @Test
    public void importAppointmentWithInvalidAttribute_TypeMismatch() {

        TaskCollection tasks_expected = new TaskCollection();
        TaskCollection tasks_actual = new TaskCollection();
        XMLCustomParser parser = new XMLCustomParser();
        String StringToParse;
        Boolean expected = false;
        Boolean actual = true;

        try {
            //<date> <day>"five"</day>
            String filePath = ".\\example\\xml_test_2";
            List<String> array = Files.readAllLines(Paths.get(filePath));
            StringToParse = String.join("\n", array);
        } catch (Exception e) {
            StringToParse = "";
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        try {
            tasks_actual = parser.importData(StringToParse);
            System.out.println(tasks_actual);
        } catch (Exception e) {
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        assertEquals(expected, actual);
    }

    @Test
    public void parseAppointmentWithInvalidAttribute_NoAttribute() {

        TaskCollection tasks_expected = new TaskCollection();
        TaskCollection tasks_actual = new TaskCollection();
        XMLCustomParser parser = new XMLCustomParser();
        String StringToParse;
        Boolean expected = false;
        Boolean actual = true;

        try {
            //no 	<location>Simple address</location>
            String filePath = ".\\example\\xml_test_3";
            List<String> array = Files.readAllLines(Paths.get(filePath));
            StringToParse = String.join("\n", array);
        } catch (Exception e) {
            StringToParse = "";
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        try {
            tasks_actual = parser.importData(StringToParse);
            System.out.println(tasks_actual);
        } catch (Exception e) {
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        assertEquals(expected, actual);
    }

    @Test
    public void parseAppointment_EmptyFile() {

        TaskCollection tasks_expected = new TaskCollection();
        TaskCollection tasks_actual = new TaskCollection();
        XMLCustomParser parser = new XMLCustomParser();
        String StringToParse;
        Boolean expected = false;
        Boolean actual = true;

        try {
            //EmptyFile
            String filePath = ".\\example\\xml_test_4";
            List<String> array = Files.readAllLines(Paths.get(filePath));
            StringToParse = String.join("\n", array);
        } catch (Exception e) {
            StringToParse = "";
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        try {
            tasks_actual = parser.importData(StringToParse);
            System.out.println(tasks_actual);
        } catch (Exception e) {
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        assertEquals(expected, actual);
    }

    @Test
    public void parseAppointment_NoOpeningElement() {

        TaskCollection tasks_expected = new TaskCollection();
        TaskCollection tasks_actual = new TaskCollection();
        XMLCustomParser parser = new XMLCustomParser();
        String StringToParse;
        Boolean expected = false;
        Boolean actual = true;

        try {
            //No Opening <tasks> Element in File
            String filePath = ".\\example\\xml_test_5";
            List<String> array = Files.readAllLines(Paths.get(filePath));
            StringToParse = String.join("\n", array);
        } catch (Exception e) {
            StringToParse = "";
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        try {
            tasks_actual = parser.importData(StringToParse);
            System.out.println(tasks_actual);
        } catch (Exception e) {
            actual = false;
            System.out.println("Parsing is unsuccessful");
        }

        assertEquals(expected, actual);
    }
}
