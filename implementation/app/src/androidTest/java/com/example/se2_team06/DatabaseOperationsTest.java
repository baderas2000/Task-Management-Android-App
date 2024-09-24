package com.example.se2_team06;

import static android.content.ContentValues.TAG;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Checklist;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.ETaskType;
import com.example.se2_team06.model.Task;
import com.example.se2_team06.model.TaskDao;
import com.example.se2_team06.model.TaskProxy;
import com.example.se2_team06.model.Time;
import com.example.se2_team06.model.notifications.NotificationSettingsDao;
import com.example.se2_team06.viewmodel.TaskViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatabaseOperationsTest {

    private AppDatabase appDatabase;
    public TaskDao taskDao;
    public TaskProxy proxy;

    @Before
    public void setup() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase =
                Room.inMemoryDatabaseBuilder(targetContext, AppDatabase.class)
                        .allowMainThreadQueries().build();
        taskDao = appDatabase.taskDao();
        proxy = TaskProxy.getInstance(AppDatabase.getDatabase(targetContext));
    }

    @Test
    public void insertTaskInDatabase(){

        Appointment input = new Appointment(
                "Test1",
                "test1",
                EColor.RED,
                EStatus.PLANNED,
                EPriority.LOW,
                "Appointment",
                "home",
                new Date(18, 0, 2023),
                ETaskType.PRIVATE);
        int outputSizeOfList = 1;

        taskDao.insertAppointment(input);

        List<Appointment> output = taskDao.getAllAppointmentTasks();

        assertEquals(outputSizeOfList, output.size());

    }

    @Test
    public void updateTaskInDatabase(){

        Appointment input = new Appointment(
                "Test1",
                "test1",
                EColor.RED,
                EStatus.PLANNED,
                EPriority.LOW,
                "Appointment",
                "home",
                new Date(18, 0, 2023),
                ETaskType.PRIVATE);
        String expectedOutput = "test2";

        taskDao.insertAppointment(input);

        List<Appointment> helper = taskDao.getAllAppointmentTasks();
        input = helper.get(0);
        input.setDescription("test2");

        taskDao.updateAppointment(input);

        helper = taskDao.getAllAppointmentTasks();
        Appointment test = helper.get(0);

        String output = test.getDescription();

        assertEquals(expectedOutput, output);

    }

    @Test
    public void deleteTaskInDatabase(){

        Appointment input = new Appointment(
                "Test1",
                "test1",
                EColor.RED,
                EStatus.PLANNED,
                EPriority.LOW,
                "Appointment",
                "home",
                new Date(18, 0, 2023),
                ETaskType.PRIVATE);
        int outputSizeOfList = 0;

        taskDao.insertAppointment(input);

        List<Appointment> output = taskDao.getAllAppointmentTasks();

        input = output.get(0);
        taskDao.deleteAppointment(input);


        output = taskDao.getAllAppointmentTasks();

        assertEquals(outputSizeOfList, output.size());

    }

    @Test
    public void insertChecklistInDatabase(){

        Checklist input = new Checklist(
                "Test1",
                "test1",
                EColor.RED,
                EStatus.PLANNED,
                EPriority.LOW,
                "Checklist",
                new Date(18, 0, 2023),
                ETaskType.PRIVATE,
                new Time(13,0));
        int outputSizeOfList = 1;

        taskDao.insertChecklist(input);

        List<Checklist> output = taskDao.getAllChecklistTasks();

        assertEquals(outputSizeOfList, output.size());
        assertEquals("Test1", output.get(0).getTitle());

    }

    @Test
    public void updateChecklistInDatabase(){

        Checklist input = new Checklist(
                "Test1",
                "test1",
                EColor.RED,
                EStatus.PLANNED,
                EPriority.LOW,
                "Checklist",
                new Date(18, 0, 2023),
                ETaskType.PRIVATE,
                new Time(13,0));
        int outputSizeOfList = 1;

        taskDao.insertChecklist(input);

        List<Checklist> output = taskDao.getAllChecklistTasks();

        input = output.get(0);
        input.setDescription("hello");
        taskDao.updateChecklist(input);


        output = taskDao.getAllChecklistTasks();

        assertEquals(outputSizeOfList, output.size());
        assertEquals("hello", output.get(0).getDescription());

    }

    @Test
    public void deleteChecklistInDatabase(){

        Checklist input = new Checklist(
                "Test1",
                "test1",
                EColor.RED,
                EStatus.PLANNED,
                EPriority.LOW,
                "Checklist",
                new Date(18, 0, 2023),
                ETaskType.PRIVATE,
                new Time(13,0));
        int outputSizeOfList = 0;

        taskDao.insertChecklist(input);

        List<Checklist> output = taskDao.getAllChecklistTasks();

        input = output.get(0);
        taskDao.deleteChecklist(input);


        output = taskDao.getAllChecklistTasks();

        assertEquals(outputSizeOfList, output.size());

    }

    /*
     * testing this not working due to threads constantly ending up with different results,
     * insert before, while and after delete, randomly
    @Test
    public void insertCorrectTaskClassInDatabase() throws InterruptedException {

        List<Task> toInsert = new ArrayList<>();
        Appointment input = new Appointment("Test1", "test1", EColor.RED, EStatus.PLANNED,EPriority.LOW, "Appointment", "home", new Date(18, 0, 2023), ETaskType.PRIVATE);
        Checklist input1 = new Checklist("Test2", "test2", EColor.RED, EStatus.PLANNED,  EPriority.LOW,  "Checklist", new Date(18, 0, 2023), ETaskType.PRIVATE, new Time(13,0));
        toInsert.add(input);
        toInsert.add(input1);
        int outputSizeOfList = 2;

        proxy.deleteAll();
        for(Task task : toInsert){
            proxy.insert(task);
        }

        List<Appointment> appointments = proxy.getAllAppointmentTasks();
        List<Checklist> checklists = proxy.getAllChecklistTasks();

        List<Task> output = new ArrayList<>();
        output.addAll(appointments);
        output.addAll(checklists);

        for(Appointment item : appointments){
            Log.d(TAG, "inside appointments we have uids: " + item.getUid() + ", with title: " + item.getTitle());
        }
        for(Checklist item : checklists){
            Log.d(TAG, "inside checklists we have uids: " + item.getUid() + ", with title: " + item.getTitle());
        }

        assertEquals(1, appointments.size());
        assertEquals(1, checklists.size());
        assertEquals(outputSizeOfList, output.size());
        assertEquals("Appointment", output.get(0).getSubclass());
        assertEquals("Checklist", output.get(1).getSubclass());

    }*/
}
