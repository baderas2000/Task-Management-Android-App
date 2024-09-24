package com.example.se2_team06.model;

import java.lang.NumberFormatException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class dedicated to parsing JSON-formatted string.
 * Contains methods to serialize and deserialize JSON-formatted string.
 */

public class JSONCustomParser extends CustomParser{

    /**
     * Methods deserializes collection of tasks
     * into Java native class from JSON-formatted string.
     * @param string JSON-formatted string.
     * @return Collection of tasks deserialized from passed string.
     */
    public TaskCollection importData(String string) throws ParsingException {

        TaskCollection tasks = new TaskCollection();
        Object obj = null;
        try {
            obj = new JSONParser().parse(string);
        } catch (ParseException e) {
            System.out.println("ParseException");
            //return tasks;
        }
        JSONObject jo = (JSONObject) this.isNotNull(obj);

        // Resolve list of task.
        JSONArray ja_tasks = (JSONArray) jo.get("tasks");

        if (ja_tasks == null) {
            System.out.println("ParseException");
            //return tasks;
        }

        for (Object task : ja_tasks) {
            if (!(task instanceof JSONObject)) {
                System.out.println("ParseException");
                //return tasks;
            }
            JSONObject jo_task = (JSONObject) task;
            tasks.addTask(this.deserializeTask(jo_task));
        }

        return tasks;

    }

    /**
     * Method parses task from 'jo' object
     * into Java native class.
     * @param jo Object, that contains serializes task.
     * @return Task, parsed from 'jo' object.
     */
    private Task deserializeTask(JSONObject jo) throws ParsingException {
        String type = (String) jo.get("class");
        if (type == null) {
            throw new ParsingException(-1, "ParseException");
        } else if (type.equals("appointment")) {
            return deserializeAppointment(jo);
        } else if (type.equals("checklist")) {
            return deserializeChecklist(jo);
        }
        throw new ParsingException(-1, "ParseException");
    }

    private Task deserializeAppointment(JSONObject jo) throws ParsingException{
        String title = (String) isNotNull(jo.get("title"));
        String description = (String) isNotNull(jo.get("description"));
        EStatus status =  EStatus.valueOf((String) isNotNull(jo.get("status")));
        EColor color = EColor.valueOf((String) isNotNull(jo.get("color")));
        EPriority priority = EPriority.valueOf((String) isNotNull(jo.get("priority")));
        ETaskType type = ETaskType.valueOf( (String) isNotNull(jo.get("type")));
        //Time time = deserializeTime( (JSONObject) isNull(jo.get("time")));

        Date date = deserializeDate( (JSONObject) isNotNull(jo.get("date")));
        String location = (String) isNotNull(jo.get("location"));

        return new Appointment(title, description, color, status, priority, "Appointment", location, date, type);
    }

    private Date deserializeDate(JSONObject jo) throws ParsingException {
        try {
            int day = Integer.parseInt(String.valueOf(isNotNull(jo.get("day"))));
            int month = Integer.parseInt(String.valueOf(isNotNull(jo.get("month"))));
            int year = Integer.parseInt(String.valueOf(isNotNull(jo.get("year"))));
            return new Date(day, month, year);
        } catch (NumberFormatException e) {
            throw new ParsingException(-1, "Invalid fields in object Date");
        }
    }

    private Time deserializeTime(JSONObject jo) throws ParsingException {
        try {
            int hour = Integer.parseInt(String.valueOf(isNotNull(jo.get("hour"))));
            int minute = Integer.parseInt(String.valueOf(isNotNull(jo.get("minute"))));
            return new Time(hour, minute);
        } catch (NumberFormatException e) {
            throw new ParsingException(-1, "Invalid fields in object Time");
        }
    }

    private Task deserializeChecklist(JSONObject jo) throws ParsingException {
        String title = (String) isNotNull(jo.get("title"));
        String description = (String) isNotNull(jo.get("description"));
        EStatus status =  EStatus.valueOf((String) isNotNull(jo.get("status")));
        EColor color = EColor.valueOf((String) isNotNull(jo.get("color")));
        EPriority priority = EPriority.valueOf((String) isNotNull(jo.get("priority")));
        ETaskType type = ETaskType.valueOf( (String) isNotNull(jo.get("type")));
        Time time = deserializeTime( (JSONObject) isNotNull(jo.get("time")));

        Date deadline = deserializeDate( (JSONObject) isNotNull(jo.get("deadline")));

        return new Checklist(title, description, color, status, priority, "Checklist", deadline, type, time);
    }

    /**
     * Method serializes collection of tasks
     * from Java native class into JSON-formatted string.
     * @param tasks Collection of tasks to be serialized.
     * @return JSON-formatted string, that contains serialized tasks.
     */
    public String exportData(TaskCollection tasks) throws ParsingException {
        JSONObject jo = new JSONObject();
        JSONArray jo_arr = new JSONArray();
        for (Iterator<Task> i = tasks.getIterator(); i.hasNext();) {
            jo_arr.add(serializeTask((Task) i.next()));
        }
        jo.put("tasks", jo_arr);
        return jo.toString();
    }

    /**
     * Method serializes task from Java native class
     * into dedicated JSON object.
     * @param task Task to be serialized.
     * @return JSON object, that contains serialized task.
     */

    private JSONObject serializeAppointment (Task task) throws ParsingException {
        if (! (task instanceof Appointment)) {
            throw new ParsingException(-1, "Serialization error: Wrong type of object to serialize");
        }
        Appointment appointment = (Appointment) task;
        JSONObject jo = new JSONObject();
        jo.put("class", "appointment");
        jo.put("priority", appointment.getPriority().toString());
        jo.put("description", appointment.getDescription());
        jo.put("status", appointment.getStatus().toString());
        jo.put("title", appointment.getTitle());
        jo.put("color", appointment.getColor().toString());
        jo.put("type", appointment.getType().toString());
        //jo.put("time", serializeTime(appointment.getTime()));
        jo.put("location", appointment.getLocation());
        jo.put("date", serializeDate(appointment.getDate()));
        return jo;
    }

    private JSONObject serializeChecklist (Task task) throws ParsingException {
        if (! (task instanceof Checklist)) {
            throw new ParsingException(-1, "Serialization error: Wrong type of object to serialize");
        }
        Checklist checklist = (Checklist) task;
        JSONObject jo = new JSONObject();
        jo.put("class", "checklist");
        jo.put("priority", checklist.getPriority().toString());
        jo.put("description", checklist.getDescription());
        jo.put("status", checklist.getStatus().toString());
        jo.put("title", checklist.getTitle());
        jo.put("color", checklist.getColor().toString());
        jo.put("type", checklist.getType().toString());
        jo.put("time", serializeTime(checklist.getTime()));
        jo.put("deadline", serializeDate(checklist.getDeadline()));
        return jo;
    }

    private JSONObject serializeTime (Time time) {
        JSONObject jo = new JSONObject();
        jo.put("hour", time.getHour());
        jo.put("minute", time.getMinute());
        return jo;
    }

    private JSONObject serializeDate (Date date) {
        JSONObject jo = new JSONObject();
        jo.put("day", date.getDay());
        jo.put("month", date.getMonth());
        jo.put("year", date.getYear());
        return jo;
    }

    private JSONObject serializeTask(Task task) throws ParsingException {
        if (task instanceof Appointment) {
            return serializeAppointment(task);
        } else if (task instanceof Checklist) {
            return serializeChecklist(task);
        }
        throw new ParsingException(-1, "Serialization error: Unexpected subclass of class Task");
    }

}
