package com.example.se2_team06.model;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se2_team06.R;
import com.example.se2_team06.view.AddTaskActivity;
import com.example.se2_team06.view.CreateChecklistActivity;
import com.example.se2_team06.view.EditMultipleTasksActivity;
import com.example.se2_team06.view.ImportExportActivity;
import com.example.se2_team06.view.UserContactActivity;
import com.example.se2_team06.viewmodel.MultiViewAdapter;
import com.example.se2_team06.viewmodel.NotificationViewModel;
import com.example.se2_team06.viewmodel.TaskAdapter;
import com.example.se2_team06.viewmodel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    static public TaskViewModel taskViewModel;
    private NotificationViewModel notificationViewModel;
    private boolean multiSelect = false;
    RecyclerView recyclerView;
    List<Task> selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TaskAdapter taskAdapter = new TaskAdapter();
        final MultiViewAdapter multiViewAdapter = new MultiViewAdapter();
        recyclerView.setAdapter(taskAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!multiSelect) {
                    /**
                     * we create a pop up menu for the user to choose which type of Task they want to add
                      */
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_creation, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.createAppointment) {
                                getTaskContent.launch(new Intent(MainActivity.this, AddTaskActivity.class));
                            }
                            if (menuItem.getItemId() == R.id.createChecklist) {
                                getTaskContent.launch(new Intent(MainActivity.this, CreateChecklistActivity.class));
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }else{
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                    popupMenu.getMenuInflater().inflate(R.menu.multi_select_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.editMulti) {
                                selected = multiViewAdapter.getSelected();
                                getTaskContent.launch(new Intent(MainActivity.this, EditMultipleTasksActivity.class));
                            }
                            if (menuItem.getItemId() == R.id.deleteMulti) {
                                deleteSelectedTasks(multiViewAdapter.getSelected());
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }
            }
        });


        /**
         * Watches over the Tasks in the database
         * IF a task changes, gets added or deleted
         * this function will notice and update the views
         */
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.setTasks(tasks);
                multiViewAdapter.setTasks(tasks);
                Log.d(TAG, "List<Task> in MainActivity 116 is now this size: " + tasks.size());
                runOnUiThread(()-> {
                    List<Task> test = taskViewModel.getAllTasks().getValue();
                    if(test != null){
                        Log.d(TAG, "size while in main thread: " + test.size());
                    }else{
                        Log.d(TAG, "MainActivity 121, null");
                    }
                });
            }
        });

        ImageButton settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserContactActivity.class);
                startActivity(intent);
            }
        });

        ImageButton importExportButton = findViewById(R.id.importExportButton);
        importExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ImportExportActivity.class);
                startActivity(intent);
            }
        });

        /**
        * When Button pressed we switch the viewHolder to be able
        * to select items and manipulate them in one go
        */
        Button selectBtn = findViewById(R.id.selectionButton);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(multiSelect){
                    selectBtn.setText("Select");
                    multiSelect = !multiSelect;
                    recyclerView.setAdapter(taskAdapter);
                    fab.setImageResource(R.drawable.ic_add);
                }else{
                    selectBtn.setText("Finish");
                    multiSelect = !multiSelect;
                    recyclerView.setAdapter(multiViewAdapter);
                    fab.setImageResource(R.drawable.ic_more_vert);

                }
            }
        });

        showTaskStateChangedPopUpNotifications();
    }


    /**
     * getTaskContent sends the user to the creation form for a Task
     * it expects return values to than create the Task
     */
    private ActivityResultLauncher<Intent> getTaskContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    String subclass = "";
                    if(data != null) {
                        subclass = data.getStringExtra(AddTaskActivity.EXTRA_SUBCLASS);
                    }

                    switch(subclass){
                        case "Appointment":
                            createAppointment(data);
                            break;
                        case "Checklist":
                            createChecklist(data);
                            break;
                        case "Update":
                            updateSelectedTasks(data);
                            break;
                        default:
                            //throw error
                            //showToast("error in switch getTaskContent");
                            break;
                    }
                }
            });


    private void showToast(String text){
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void createAppointment(Intent data){
        String title = data.getStringExtra(AddTaskActivity.EXTRA_TITLE);
        String description = data.getStringExtra(AddTaskActivity.EXTRA_DESCRIPTION);

        EColor color = (EColor) data.getSerializableExtra(AddTaskActivity.EXTRA_COLOR);
        EPriority priority = (EPriority) data.getSerializableExtra(AddTaskActivity.EXTRA_PRIORITY);
        ETaskType taskType = (ETaskType) data.getSerializableExtra(AddTaskActivity.EXTRA_TASK_TYPE);

        String location = data.getStringExtra(AddTaskActivity.EXTRA_LOCATION);

        String dateString = data.getStringExtra(AddTaskActivity.EXTRA_DATE);
        Date date = parseDate(dateString);

        Task toBeInserted = new Appointment(title, description, color, EStatus.PLANNED, priority, "Appointment", location, date, taskType);
        taskViewModel.insert(toBeInserted);
    }

    private void createChecklist(Intent data){
        String title = data.getStringExtra(CreateChecklistActivity.EXTRA_TITLE);
        String description = data.getStringExtra(CreateChecklistActivity.EXTRA_DESCRIPTION);

        EColor color = (EColor) data.getSerializableExtra(CreateChecklistActivity.EXTRA_COLOR);
        EPriority priority = (EPriority) data.getSerializableExtra(CreateChecklistActivity.EXTRA_PRIORITY);
        ETaskType taskType = (ETaskType) data.getSerializableExtra(CreateChecklistActivity.EXTRA_TASK_TYPE);

        String dateString = data.getStringExtra(CreateChecklistActivity.EXTRA_DATE);
        String timeString = data.getStringExtra(CreateChecklistActivity.EXTRA_TIME);
        Date date = parseDate(dateString);
        Time time = parseTime(timeString);


        Task toBeInserted = new Checklist(title, description, color, EStatus.ACTIVE, priority, "Checklist", date, taskType, time);
        taskViewModel.insert(toBeInserted);
    }

    private void updateSelectedTasks(Intent data){
        EPriority prio = (EPriority) data.getSerializableExtra(EditMultipleTasksActivity.EXTRA_PRIORITY);
        for(Task task : selected){
            task.setPriority(prio);
            taskViewModel.update(task);
        }

    }

    private void deleteSelectedTasks(List<Task> tasks){
        for(Task task : tasks){
            taskViewModel.delete(task);
        }
    }

    private void showTaskStateChangedPopUpNotifications() {

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        notificationViewModel.getPopUpNotificationMessage().observe(this, new Observer<Pair<String, String>>() {
            @Override
            public void onChanged(Pair<String, String> popUpNotificationTexts) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!isFinishing()) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle(popUpNotificationTexts.first)
                                    .setMessage(popUpNotificationTexts.second)
                                    .setCancelable(false)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }
                    }
                });
            }
        });
    }

    private Date parseDate(String text){
        String[] parts = text.split("\\.");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        return new Date(day, (month-1), year);
    }

    private Time parseTime(String text){
        String[] parts = text.split("\\:");

        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        return new Time(hour, minute);
    }
}