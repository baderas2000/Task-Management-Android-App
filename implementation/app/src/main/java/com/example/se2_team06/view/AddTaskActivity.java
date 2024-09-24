package com.example.se2_team06.view;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.R;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.ETaskType;
import com.example.se2_team06.model.NoTitleException;
import com.example.se2_team06.model.Time;

import java.util.Calendar;


public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_SUBCLASS =
            "com.example.se2_team06.view.EXTRA_SUBCLASS";
    public static final String EXTRA_TITLE =
            "com.example.se2_team06.view.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.se2_team06.view.EXTRA_DESCRIPTION";
    public static final String EXTRA_STATUS =
            "com.example.se2_team06.view.EXTRA_STATUS";
    public static final String EXTRA_COLOR =
            "com.example.se2_team06.view.EXTRA_COLOR";
    public static final String EXTRA_PRIORITY =
            "com.example.se2_team06.view.EXTRA_PRIORITY";
    public static final String EXTRA_LOCATION =
            "com.example.se2_team06.view.EXTRA_LOCATION";
    public static final String EXTRA_TASK_TYPE =
            "com.example.se2_team06.view.EXTRA_TASK_TYPE";
    public static final String EXTRA_DATE =
            "com.example.se2_team06.view.EXTRA_DATE";


    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextLocation;
    private TextView showDate;
    private Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    private String subclass = "Appointment";
    private EStatus status = EStatus.PLANNED;
    private EColor color;
    private EPriority priority;
    private String location;
    private ETaskType taskType;
    private Date date = new Date(day, month, year);

    private Button displayDate;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    Spinner spinnerColor;
    Spinner spinnerPriority;
    Spinner spinnerTaskType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_description);
        editTextLocation = findViewById(R.id.edit_location);

        spinnerColor = findViewById(R.id.spinnerColor);
        spinnerColor.setAdapter(new ArrayAdapter<EColor>(this, android.R.layout.simple_spinner_item, EColor.values()));

        spinnerPriority = findViewById(R.id.spinnerPriority);
        spinnerPriority.setAdapter(new ArrayAdapter<EPriority>(this, android.R.layout.simple_spinner_item, EPriority.values()));

        spinnerTaskType = findViewById(R.id.spinnerTaskType);
        spinnerTaskType.setAdapter(new ArrayAdapter<ETaskType>(this, android.R.layout.simple_spinner_item, ETaskType.values()));

        showDate = findViewById(R.id.showDate);
        showDate.setText(date.toString());

        displayDate = findViewById(R.id.selectDate);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        AddTaskActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        dateSetListener,
                        year, month, day);

                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pickedYear, int pickedMonth, int pickedDay) {
                day = pickedDay;
                month = pickedMonth;
                year = pickedYear;

                date = new Date(day, month, year);
                showDate.setText(date.toString());
            }
        };

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        location = editTextLocation.getText().toString();

        String textColor = spinnerColor.getSelectedItem().toString();
        String textPriority = spinnerPriority.getSelectedItem().toString();
        String textTaskType = spinnerTaskType.getSelectedItem().toString();

        color = EColor.valueOf(textColor);
        priority = EPriority.valueOf(textPriority);
        taskType = ETaskType.valueOf(textTaskType);

        try{
            checkTitle(title);
        }catch(NoTitleException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_SUBCLASS, subclass);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_STATUS, status);
        data.putExtra(EXTRA_COLOR, color);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_TASK_TYPE, taskType);
        data.putExtra(EXTRA_DATE, date.toString());

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        if(text == subclass){
            Log.d(TAG, "onItemSelected() returned: " + "text is like subclass");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean checkTitle(String title) throws NoTitleException{
        if (title.trim().isEmpty()) {
            throw new NoTitleException("Title must not be empty.");
        }
        return true;
    }
}