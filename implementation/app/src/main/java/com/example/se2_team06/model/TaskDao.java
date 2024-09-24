package com.example.se2_team06.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM appointment_table JOIN checklist_table")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM appointment_table")
    LiveData<List<Appointment>> getAllAppointments();

    @Query("SELECT * FROM appointment_table")
    List<Appointment> getAllAppointmentTasks();

    @Query("SELECT * FROM checklist_table")
    LiveData<List<Checklist>> getAllChecklists();

    @Query("SELECT * FROM checklist_table")
    List<Checklist> getAllChecklistTasks();

    @Insert
    void insertChecklist(Checklist task);

    @Insert
    void insertAppointment(Appointment task);

    @Delete
    void deleteAppointment(Appointment task);

    @Delete
    void deleteChecklist(Checklist task);

    @Query("DELETE FROM appointment_table")
    void deleteAllFromAppointment();

    @Query("DELETE FROM checklist_table")
    void deleteAllFromChecklist();

    @Update
    void updateAppointment(Appointment task);

    @Update
    void updateChecklist(Checklist task);

    @Transaction
    default void deleteAllTasks(){
        deleteAllFromAppointment();
        deleteAllFromChecklist();
    }
}
