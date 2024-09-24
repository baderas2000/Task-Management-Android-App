package com.example.se2_team06;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Checklist;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.ETaskType;
import com.example.se2_team06.model.JSONCustomParser;
import com.example.se2_team06.model.TaskCollection;
import com.example.se2_team06.model.Time;
import com.example.se2_team06.model.ParsingException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JSONCustomParserUnitTest {

    @Test
    public void addAppointment() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Appointment appointment = new Appointment(
                "Shopping",
                "Buy shoes",
                EColor.GREEN,
                EStatus.DONE,
                EPriority.HIGH,
                "Appointment",
                "Graben",
                new Date(11, 1, 2023),
                ETaskType.PRIVATE
        );

        tasks.addTask(appointment);
        String result = "";

        String expected = "{\"tasks\":[{\"date\":{\"month\":1,\"year\":2023,\"day\":11},\"color\":\"GREEN\",\"description\":\"Buy shoes\",\"location\":\"Graben\",\"priority\":\"HIGH\",\"title\":\"Shopping\",\"type\":\"PRIVATE\",\"class\":\"appointment\",\"status\":\"DONE\"}]}";
        try {
            result = parser.exportData(tasks);
            System.out.println(result);
        } catch (ParsingException e) {
            System.out.println("Parsing is unsuccessful");
        }
        assertEquals(expected, result);
    }

    @Test
    public void addChecklist() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Checklist checklist = new Checklist(
                "Presentation",
                "Make presentation about project management",
                EColor.GREEN,
                EStatus.DONE,
                EPriority.HIGH,
                "Checklist",
                new Date(11, 1, 2023),
                ETaskType.BUSINESS,
                new Time(10, 00)
        );

        tasks.addTask(checklist);
        String result = "";

        String expected = "{\"tasks\":[{\"color\":\"GREEN\",\"description\":\"Make presentation about project management\",\"time\":{\"hour\":10,\"minute\":0},\"priority\":\"HIGH\",\"title\":\"Presentation\",\"type\":\"BUSINESS\",\"deadline\":{\"month\":1,\"year\":2023,\"day\":11},\"class\":\"checklist\",\"status\":\"DONE\"}]}";
        try {
            result = parser.exportData(tasks);
            System.out.println(result);
        } catch (ParsingException e) {
            System.out.println("Parsing is unsuccessful");
        }
        assertEquals(expected, result);
    }

    @Test
    public void addAppointmentAndChecklist_1() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Appointment appointment = new Appointment(
                "task",
                "description",
                EColor.BLACK,
                EStatus.ACTIVE,
                EPriority.HIGH,
                "Appointment",
                "Strasse 15",
                new Date(8, 1, 2023),
                ETaskType.BUSINESS
        );
        Checklist checklist = new Checklist(
                "checklist",
                "description",
                EColor.BLACK,
                EStatus.ACTIVE,
                EPriority.HIGH,
                "Checklist",
                new Date(8, 1, 2023),
                ETaskType.BUSINESS,
                new Time(11, 30)
        );
        tasks.addTask(appointment);
        tasks.addTask(checklist);
        String result = "";
        String expected = "{\"tasks\":[{\"date\":{\"month\":1,\"year\":2023,\"day\":8},\"color\":\"BLACK\",\"description\":\"description\",\"location\":\"Strasse 15\",\"priority\":\"HIGH\",\"title\":\"task\",\"type\":\"BUSINESS\",\"class\":\"appointment\",\"status\":\"ACTIVE\"},{\"color\":\"BLACK\",\"description\":\"description\",\"time\":{\"hour\":11,\"minute\":30},\"priority\":\"HIGH\",\"title\":\"checklist\",\"type\":\"BUSINESS\",\"deadline\":{\"month\":1,\"year\":2023,\"day\":8},\"class\":\"checklist\",\"status\":\"ACTIVE\"}]}";
        try {
            result = parser.exportData(tasks);
            System.out.println(result);
        } catch (ParsingException e) {
            System.out.println("Parsing is unsuccessful");
        }
        assertEquals(expected, result);
    }

    @Test
    public void addAppointmentAndChecklist_2() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Appointment appointment = new Appointment(
                "Date",
                "Romantic dinner",
                EColor.BLUE,
                EStatus.PLANNED,
                EPriority.MEDIUM,
                "Appointment",
                "Herrengasse 14, 1010 Wien",
                new Date(10, 1, 2023),
                ETaskType.PRIVATE
        );
        Checklist checklist = new Checklist(
                "Cook dinner",
                "Cook risotto with shrimps",
                EColor.RED,
                EStatus.CANCELLED,
                EPriority.HIGH,
                "Checklist",
                new Date(10, 1, 2023),
                ETaskType.PRIVATE,
                new Time(20, 00)
        );
        tasks.addTask(appointment);
        tasks.addTask(checklist);
        String result = "";

        String expected = "{\"tasks\":[{\"date\":{\"month\":1,\"year\":2023,\"day\":10},\"color\":\"BLUE\",\"description\":\"Romantic dinner\",\"location\":\"Herrengasse 14, 1010 Wien\",\"priority\":\"MEDIUM\",\"title\":\"Date\",\"type\":\"PRIVATE\",\"class\":\"appointment\",\"status\":\"PLANNED\"},{\"color\":\"RED\",\"description\":\"Cook risotto with shrimps\",\"time\":{\"hour\":20,\"minute\":0},\"priority\":\"HIGH\",\"title\":\"Cook dinner\",\"type\":\"PRIVATE\",\"deadline\":{\"month\":1,\"year\":2023,\"day\":10},\"class\":\"checklist\",\"status\":\"CANCELLED\"}]}";
        try {
            result = parser.exportData(tasks);
            System.out.println(result);
        } catch (ParsingException e) {
            System.out.println("Parsing is unsuccessful");
        }
        assertEquals(expected, result);
    }

    @Test
    public void parseAppointment() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Boolean result = true;
        Boolean expected = true;

        String StringToParse = "{\"tasks\":[{\"date\":{\"month\":1,\"year\":2023,\"day\":11},\"color\":\"GREEN\",\"description\":\"Buy shoes\",\"location\":\"Graben\",\"priority\":\"HIGH\",\"title\":\"Shopping\",\"type\":\"PRIVATE\",\"class\":\"appointment\",\"status\":\"DONE\"}]}";

        try {
            tasks = parser.importData(StringToParse);
            System.out.println(tasks.toString());
        } catch (ParsingException e) {
            result = false;
            System.out.println("Parsing is unsuccessful");
        }
        assertEquals(expected, result);
    }

    @Test
    public void parseChecklist() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Boolean result = true;
        Boolean expected = true;

        String StringToParse = "{\"tasks\":[{\"color\":\"GREEN\",\"description\":\"Make presentation about project management\",\"time\":{\"hour\":10,\"minute\":0},\"priority\":\"HIGH\",\"title\":\"Presentation\",\"type\":\"BUSINESS\",\"deadline\":{\"month\":1,\"year\":2023,\"day\":11},\"class\":\"checklist\",\"status\":\"DONE\"}]}";

        try {
            tasks = parser.importData(StringToParse);
            System.out.println("TASKS: " + tasks);
        } catch (ParsingException e) {
            result = false;
            System.out.println("Parsing is unsuccessful");
        }
        assertEquals(expected, result);
    }

    @Test
    public void parseAppointmentWithInvalidAttribute_TypeMismatch() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Boolean result = false;

        //description : 2500
        String StringToParse = "{\"tasks\":[{\"date\":{\"month\":1,\"year\":2023,\"day\":11},\"color\":\"GREEN\",\"description\":2500,\"location\":\"Graben\",\"priority\":\"HIGH\",\"title\":\"Shopping\",\"type\":\"PRIVATE\",\"class\":\"appointment\",\"status\":\"DONE\"}]}";

        Boolean expected = true;
        try {
            tasks = parser.importData(StringToParse);
            System.out.println(tasks);
        } catch (ClassCastException | ParsingException e) {
            System.out.println("Parsing is unsuccessful");
            result = true;
        }
        assertEquals(expected, result);
    }

    @Test
    public void parseAppointmentWithInvalidAttribute_NoAttribute() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Boolean result = false;

        //no "color":"GREEN"
        String StringToParse = "{\"tasks\":[{\"date\":{\"month\":1,\"year\":2023,\"day\":11},\"description\":\"Buy shoes\",\"location\":\"Graben\",\"priority\":\"HIGH\",\"title\":\"Shopping\",\"type\":\"PRIVATE\",\"class\":\"appointment\",\"status\":\"DONE\"}]}";

        Boolean expected = true;
        try {
            tasks = parser.importData(StringToParse);
            System.out.println(tasks);
        } catch (ClassCastException | ParsingException e) {
            System.out.println("Parsing is unsuccessful");
            result = true;
        }
        assertEquals(expected, result);
    }

    @Test
    public void parseAppointment_EmptyFile() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Boolean result = false;

        String StringToParse = " ";

        Boolean expected = true;
        try {
            tasks = parser.importData(StringToParse);
            System.out.println("TASKS" + tasks);
        } catch (NullPointerException | ParsingException e) {
            System.out.println("Parsing is unsuccessful");
            result = true;
        }
        assertEquals(expected, result);
    }

    @Test
    public void parseAppointment_NoOppeningElement() {

        TaskCollection tasks = new TaskCollection();
        JSONCustomParser parser = new JSONCustomParser();
        Boolean result = false;

        String StringToParse = "\"tasks\":[{\"date\":{\"month\":1,\"year\":2023,\"day\":11},\"color\":\"GREEN\",\"description\":\"Buy shoes\",\"location\":\"Graben\",\"priority\":\"HIGH\",\"title\":\"Shopping\",\"type\":\"PRIVATE\",\"class\":\"appointment\",\"status\":\"DONE\"}]}";

        Boolean expected = true;
        try {
            tasks = parser.importData(StringToParse);
            System.out.println(tasks);
        } catch (NullPointerException | ParsingException e) {
            System.out.println("Parsing is unsuccessful");
            result = true;
        }
        assertEquals(expected, result);
    }

    @Test
    public void checkEqualsMethod() {

        TaskCollection collection1 = new TaskCollection();
        TaskCollection collection2 = new TaskCollection();
        Boolean result;
        Boolean expected = true;
        JSONCustomParser parser = new JSONCustomParser();
        Appointment appointment1 = new Appointment(
                "Shopping",
                "Buy shoes",
                EColor.GREEN,
                EStatus.DONE,
                EPriority.HIGH,
                "Appointment",
                "Graben",
                new Date(11, 1, 2023),
                ETaskType.PRIVATE
        );

        Appointment appointment2 = new Appointment(
                "Shopping2",
                "Buy shoes",
                EColor.RED,
                EStatus.DONE,
                EPriority.HIGH,
                "Appointment",
                "Graben",
                new Date(11, 1, 2023),
                ETaskType.PRIVATE
        );

        collection1.addTask(appointment1);
        collection2.addTask(appointment2);
        if (collection1.equals(collection2))
            result = false;
        else
            result = true;

        assertEquals(expected, result);
    }

}