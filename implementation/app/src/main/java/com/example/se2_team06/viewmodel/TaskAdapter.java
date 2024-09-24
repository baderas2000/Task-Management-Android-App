package com.example.se2_team06.viewmodel;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se2_team06.R;
import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Checklist;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.MainActivity;
import com.example.se2_team06.model.Task;
import com.example.se2_team06.view.AddTaskActivity;
import com.example.se2_team06.view.EditAppointmentActivity;
import com.example.se2_team06.view.EditChecklistActivity;

import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/26245139/how-to-create-recyclerview-with-multiple-view-types
public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class TaskHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;

        public TaskHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
        }

        void bind(final Task task){

            textViewTitle.setText(task.getTitle());
            textViewDescription.setText(task.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Task itemData = tasks.get(position);

                    if (itemData instanceof Appointment) {
                        Log.d(TAG, "TaskAdapter: is appointment.");
                        updateAppointment((Appointment) itemData, view);
                    } else if (itemData instanceof Checklist){
                        Log.d(TAG, "TaskAdapter: is not appointment.");
                        updateChecklist((Checklist) itemData, view);
                    }

                }
            });
        }

    }

    class ChecklistHolder extends RecyclerView.ViewHolder{

        private TextView textViewTitle;
        private TextView textViewDescription;
        private ImageView checkBox;

        public ChecklistHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            checkBox = itemView.findViewById(R.id.checkbox_item);
        }

        void bind(final Task task){

            textViewTitle.setText(task.getTitle());
            textViewDescription.setText(task.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Task itemData = tasks.get(position);

                    updateChecklist((Checklist) itemData, view);


                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Task itemData = tasks.get(position);
                    if(itemData.getStatus().equals(EStatus.ACTIVE)){
                        itemData.setStatus(EStatus.DONE);
                        checkBox.setImageResource(R.drawable.ic_check_box_full);
                    }else{
                        itemData.setStatus(EStatus.ACTIVE);
                        checkBox.setImageResource(R.drawable.ic_check_box_empty);
                    }
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {

        Task task = tasks.get(position);
        if(task instanceof Appointment){
            return 0;
        }
        return 1;
    }


    private List<Task> tasks = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        switch (viewType) {
            case 0:
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.task_item, parent, false);
                return new TaskHolder(itemView);
            case 1:
                View itemView1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.checklist_item, parent, false);
                return new ChecklistHolder(itemView1);
            default:

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Task item = tasks.get(position);
        switch (item.getSubclass()) {
            case "Appointment":
                TaskHolder taskHolder = (TaskHolder) holder;
                Task task = tasks.get(position);
                taskHolder.bind(task);
                break;

            case "Checklist":
                ChecklistHolder checklistHolder = (ChecklistHolder)holder;
                Task task1 = tasks.get(position);
                checklistHolder.bind(task1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //TODO create edit for checklist


    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public List<Task> getAll() {
        return tasks;
    }

    private void updateAppointment(Appointment task, View view){
        Intent intent = new Intent(view.getContext(), EditAppointmentActivity.class);

        intent.putExtra("uid", task.getUid());
        intent.putExtra("title", task.getTitle());
        intent.putExtra("description", task.getDescription());
        intent.putExtra("location", task.getLocation());
        intent.putExtra("color", task.getColor());
        intent.putExtra("status", task.getStatus());
        intent.putExtra("priority", task.getPriority());
        intent.putExtra("taskType", task.getType());
        intent.putExtra("date", task.getDate().toString());

        view.getContext().startActivity(intent);
    }

    private void updateChecklist(Checklist task, View view){
        Intent intent = new Intent(view.getContext(), EditChecklistActivity.class);

        intent.putExtra("uid", task.getUid());
        intent.putExtra("title", task.getTitle());
        intent.putExtra("description", task.getDescription());
        intent.putExtra("color", task.getColor());
        intent.putExtra("status", task.getStatus());
        intent.putExtra("priority", task.getPriority());
        intent.putExtra("taskType", task.getType());
        intent.putExtra("date", task.getDeadline().toString());
        intent.putExtra("time", task.getTime().toString());

        view.getContext().startActivity(intent);
    }
}
