package com.example.se2_team06.model.notifications;

import androidx.lifecycle.LiveData;

import com.example.se2_team06.model.AppDatabase;

import java.util.List;

public class UserContactRepository {

    private UserContactDao userContactDao;
    private LiveData<List<UserContact>> allUserContacts;

    public UserContactRepository(UserContactDao userContactDao) {
        this.userContactDao = userContactDao;
        allUserContacts = userContactDao.getAllUserContacts();
    }

    public LiveData<List<UserContact>> getAllUserContacts() {
        return allUserContacts;
    }

    public void insert(UserContact userContact) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userContactDao.insert(userContact);
        });
    }

    public void update(UserContact userContact) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userContactDao.update(userContact);
        });
    }

    public UserContact getByContactType(UserContactType contactType) {
        return userContactDao.getByContactType(contactType);
    }
}
