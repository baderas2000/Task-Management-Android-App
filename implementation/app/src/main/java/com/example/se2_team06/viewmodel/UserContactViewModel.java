package com.example.se2_team06.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.notifications.UserContact;
import com.example.se2_team06.model.notifications.UserContactRepository;

import java.util.List;
import java.util.Optional;

public class UserContactViewModel extends AndroidViewModel {

    private UserContactRepository userContactRepository;

    private final LiveData<List<UserContact>> allUserContacts;

    public UserContactViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        userContactRepository = new UserContactRepository(database.userContactDao());
        allUserContacts = userContactRepository.getAllUserContacts();
    }

    public LiveData<List<UserContact>> getAllUserContacts() {
        return allUserContacts;
    }

    public void saveUserContact(UserContact userContact) {
        List<UserContact> userContacts = allUserContacts.getValue();
        if (userContacts != null) {
            Optional<UserContact> optionalUserContact = userContacts.stream()
                    .filter(c -> c.getContactType().equals(userContact.getContactType()))
                    .findAny();
            if (optionalUserContact.isPresent()) {
                UserContact contact = optionalUserContact.get();
                contact.setContactValue(userContact.getContactValue());
                update(contact);
                return;
            }
        }
        insert(userContact);
    }

    public void insert(UserContact userContact) {
        userContactRepository.insert(userContact);
    }

    public void update(UserContact userContact) {
        userContactRepository.update(userContact);
    }
}
