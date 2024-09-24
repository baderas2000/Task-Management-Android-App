package com.example.se2_team06.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_team06.R;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;

public class EditMultipleTasksActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static final String EXTRA_PRIORITY =
            "com.example.se2_team06.view.EXTRA_PRIORITY";

    private String subclass = "Update";
    private EPriority priority;
    Spinner spinnerPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_multiple_tasks);

        spinnerPriority = findViewById(R.id.spinnerPriority);
        spinnerPriority.setAdapter(new ArrayAdapter<EPriority>(this, android.R.layout.simple_spinner_item, EPriority.values()));

        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Update Tasks");
    }

    private void saveTask() {

        Intent data = new Intent();

        String textPriority = spinnerPriority.getSelectedItem().toString();
        priority = EPriority.valueOf(textPriority);

        data.putExtra(AddTaskActivity.EXTRA_SUBCLASS, subclass);
        data.putExtra(EXTRA_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String text = adapterView.getItemAtPosition(position).toString();
        if(text == subclass){
            Log.d(TAG, "onItemSelected() returned: " + "text is not like subclass");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
