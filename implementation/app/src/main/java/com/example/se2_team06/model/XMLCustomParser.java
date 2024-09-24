package com.example.se2_team06.model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringReader;
import java.io.StringWriter;

import org.xml.sax.InputSource;

/**
 * Class dedicated to parsing XML-formatted string.
 * Contains methods to serialize and deserialize XML-formatted string.
 */

public class XMLCustomParser extends CustomParser {

    String title = "title";
    String description = "description";
    EColor color = EColor.BLACK;
    EStatus status = EStatus.PLANNED;
    EPriority priority = EPriority.LOW;
    ETaskType type = ETaskType.BUSINESS;

    Document document;
    Element element;

    public TaskCollection importData(String string) throws ParsingException {
        TaskCollection tasks = new TaskCollection();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(string)));
            NodeList nodeList = document.getElementsByTagName("task");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                tasks.addTask(this.deserializeTask(node));
            }
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ParsingException(1, "Invalid XML format.");
        }
        return tasks;
    }

    private Task deserializeTask (Node node) throws ParsingException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String type = element.getAttribute("class");
            this.title = this.getNode(element, "title").getTextContent();
            this.description = this.getNode(element, "description").getTextContent();
            this.color = EColor.valueOf(this.getNode(element, "color").getTextContent());
            this.status = EStatus.valueOf(this.getNode(element, "status").getTextContent());
            this.priority = EPriority.valueOf(this.getNode(element, "priority").getTextContent());
            this.type = ETaskType.valueOf(this.getNode(element, "type").getTextContent());
            if (type.equals("appointment")) {
                return deserializeAppointment(element);
            } else if (type.equals("checklist")) {
               return deserializeChecklist(element);
            }
        }
        throw new ParsingException(1, "Unknown task type.");
    }

    private Task deserializeAppointment (Element element) throws ParsingException {
        Date date = this.deserializeDate(this.getNode(element, "date"));
        String location = this.getNode(element, "location").getTextContent();
        return new Appointment(
                this.title,
                this.description,
                this.color,
                this.status,
                this.priority,
                "Appointment",
                location,
                date,
                this.type
        );

    }

    private Task deserializeChecklist (Element element) throws ParsingException {
        Date deadline = this.deserializeDate(this.getNode(element, "deadline"));
        Time time = this.deserializeTime(this.getNode(element, "time"));
        return new Checklist(
                this.title,
                this.description,
                this.color,
                this.status,
                this.priority,
                "Checklist",
                deadline,
                this.type,
                time
        );

    }

    private Date deserializeDate (Node node) throws ParsingException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            try {
                Element element = (Element) node;
                Node monthNode = this.getNode(element, "month");
                int month = Integer.parseInt(monthNode.getTextContent());
                Node dayNode = this.getNode(element, "day");
                int day = Integer.parseInt(dayNode.getTextContent());
                Node yearNode = this.getNode(element, "year");
                int year = Integer.parseInt(yearNode.getTextContent());
                return new Date(day, month, year);
            } catch (NumberFormatException e) {
                //e.printStackTrace();
                throw new ParsingException(1, "Invalid fields in object Date.");
            }
        }
        throw new ParsingException(1, "Can not cast node to element.");
    }

    private Time deserializeTime (Node node) throws ParsingException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            try {
                Element element = (Element) node;
                Node minuteNode = this.getNode(element, "minute");
                int minute = Integer.parseInt(minuteNode.getTextContent());
                Node hourNode = this.getNode(element, "hour");
                int hour = Integer.parseInt(hourNode.getTextContent());
                return new Time(hour, minute);
            } catch (NumberFormatException e) {
                throw new ParsingException(1, "Invalid fields in object Time.");
            }
        }
        throw new ParsingException(1, "Can not cast node to element.");
    }

    private Node getNode(Element element, String field) throws ParsingException{
        return (Node) this.isNotNull(element.getElementsByTagName(field).item(0));
    }

    public String exportData(TaskCollection tasks) throws ParsingException {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            this.document = documentBuilder.newDocument();
            Element root_element = this.document.createElement("tasks");
            document.appendChild(root_element);
            for (Iterator<Task> i = tasks.getIterator(); i.hasNext();) {
                root_element.appendChild(this.serializeTask(i.next()));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new StringWriter());
            transformer.transform(domSource, streamResult);
            return streamResult.getWriter().toString();
        } catch (ParserConfigurationException | TransformerException e) {
            //e.printStackTrace();
            throw new ParsingException(1, "ParserConfigurationException");
        }
    }

    private Element serializeTask(Task task) throws ParsingException{
        element = this.document.createElement("task");
        this.addTextElement(element, "title", task.getTitle());
        this.addTextElement(element, "description", task.getDescription());
        this.addTextElement(element, "color", task.getColor().toString());
        this.addTextElement(element, "status", task.getStatus().toString());
        this.addTextElement(element, "priority", task.getPriority().toString());
        if (task instanceof Appointment) {
            return this.serializeAppointment(element, task);
        } else if (task instanceof Checklist) {
            return this.serializeChecklist(element, task);
        } else {
            throw new ParsingException(1, "Unexpected subclass of class Task.");
        }
    }

    private Element serializeAppointment(Element element, Task task) {
        Appointment appointment = (Appointment) task;
        element.setAttribute("class", "appointment");
        this.addTextElement(element, "location", appointment.getLocation());
        this.addTextElement(element, "type", appointment.getType().toString());
        this.element.appendChild(this.serializeDate(appointment.getDate(), "date"));
        return element;   
    }

    private Element serializeChecklist(Element element, Task task) {
        Checklist checklist = (Checklist) task;
        element.setAttribute("class", "checklist");
        this.addTextElement(element, "type", checklist.getType().toString());
        element.appendChild(this.serializeDate(checklist.getDeadline(), "deadline"));
        element.appendChild(this.serializeTime(checklist.getTime(), "time"));
        return element;
    }
    
    private Element serializeDate(Date date, String name) {
        Element element = this.document.createElement(name);
        this.addTextElement(element, "day", String.valueOf(date.getDay()));
        this.addTextElement(element, "month", String.valueOf(date.getMonth()));
        this.addTextElement(element, "year", String.valueOf(date.getYear()));
        return element;
    }

    private Element serializeTime(Time time, String name) {
        Element element = this.document.createElement(name);
        this.addTextElement(element, "minute", String.valueOf(time.getMinute()));
        this.addTextElement(element, "hour", String.valueOf(time.getHour()));
        return element;
    }

    private void addTextElement(Element ancestor , String name, String value) {
        Element element = this.document.createElement(name);
        element.appendChild(this.document.createTextNode(value));
        ancestor.appendChild(element);

    }

}
