package com.example.se2_team06.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_team06.R;
import com.example.se2_team06.viewmodel.TaskListCalendarViewModel;

public class TaskListCalendarActivity extends AppCompatActivity {

    private TaskListCalendarViewModel taskListCalendarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_contact);
    }
}