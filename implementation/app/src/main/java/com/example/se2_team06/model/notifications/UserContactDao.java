package com.example.se2_team06.model.notifications;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserContactDao {

    @Query(("SELECT * FROM user_contact"))
    LiveData<List<UserContact>> getAllUserContacts();

    @Query("SELECT * FROM user_contact WHERE contact_type LIKE :contactType")
    UserContact getByContactType(UserContactType contactType);

    @Insert
    void insert(UserContact userContact);

    @Update
    void update(UserContact userContact);

    @Delete
    void delete(UserContact userContact);
}
