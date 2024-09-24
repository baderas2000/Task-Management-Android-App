package com.example.se2_team06;

import static org.junit.Assert.assertEquals;

import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.ETaskType;
import com.example.se2_team06.model.JSONCustomParser;
import com.example.se2_team06.model.TaskCollection;

import org.junit.Test;

public class TaskCollectionTest {

    @Test
    public void addAppointment() {

        TaskCollection tasks = new TaskCollection();
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
        tasks.addTask(appointment);
        System.out.println(tasks.toString());

    }
}