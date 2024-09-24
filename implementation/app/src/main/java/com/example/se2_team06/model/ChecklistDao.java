package com.example.se2_team06.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChecklistDao {
    @Query("SELECT * FROM checklist_table")
    LiveData<List<Checklist>> getAll();

    @Query("SELECT * FROM checklist_table WHERE uid IN (:taskIds)")
    LiveData<List<Checklist>> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM checklist_table WHERE titel LIKE :search LIMIT 1")
    LiveData<Checklist> findByName(String search);

    @Insert
    void insertAll(Checklist task);

    @Delete
    void delete(Checklist task);

    @Update
    void update(Checklist task);

    @Query("DELETE FROM checklist_table")
    void deleteAll();
}
