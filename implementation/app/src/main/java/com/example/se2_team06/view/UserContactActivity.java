package com.example.se2_team06.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.se2_team06.R;
import com.example.se2_team06.model.notifications.UserContact;
import com.example.se2_team06.model.notifications.UserContactType;
import com.example.se2_team06.viewmodel.UserContactViewModel;

public class UserContactActivity extends AppCompatActivity {

    private UserContactViewModel userContactViewModel;
    private UserContact emailUserContact = new UserContact();
    private UserContact nameUserContact = new UserContact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contact);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText userName = findViewById(R.id.editTextUserName);
        EditText email = findViewById(R.id.editTextEmail);

        userContactViewModel =
                new ViewModelProvider(this).get(UserContactViewModel.class);

        userContactViewModel.getAllUserContacts().observe(this, contacts -> {
            for (UserContact contact : contacts) {
                if (contact.getContactType().equals(UserContactType.EMAIL)) {
                    emailUserContact = contact;
                    email.setText(emailUserContact.getContactValue());
                }
                if (contact.getContactType().equals(UserContactType.NAME)) {
                    nameUserContact = contact;
                    userName.setText(nameUserContact.getContactValue());
                }
            }
        });


        Button notificationSettingsButton = findViewById(R.id.notificationSettingsButton);
        notificationSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserContactActivity.this, NotificationsSettingsActivity.class);
                startActivity(intent);
            }
        });

        Button saveUserContactChanges = findViewById(R.id.saveUserSettingsButton);
        saveUserContactChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText() != null && EmailValidator.isValid(email.getText().toString())) {
                    UserContact userContact = new UserContact(UserContactType.EMAIL, email.getText().toString());
                    userContactViewModel.saveUserContact(userContact);
                }
                if (userName.getText() != null && !email.getText().toString().isEmpty()) {
                    UserContact userContact = new UserContact(UserContactType.NAME, userName.getText().toString());
                    userContactViewModel.saveUserContact(userContact);
                }

                Toast.makeText(UserContactActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
