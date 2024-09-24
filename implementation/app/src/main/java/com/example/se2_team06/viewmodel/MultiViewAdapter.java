package com.example.se2_team06.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se2_team06.R;
import com.example.se2_team06.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MultiViewAdapter extends RecyclerView.Adapter<MultiViewAdapter.MultiViewHolder> {

        private List<Task> tasks = new ArrayList<>();

        @NonNull
        @Override
        public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.task_item, parent, false);
            return new MultiViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
            holder.bind(tasks.get(position));
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        public void setTasks(List<Task> tasks){
            this.tasks = tasks;
            notifyDataSetChanged();
        }

        public class MultiViewHolder extends RecyclerView.ViewHolder {

            private TextView title;
            private ImageView tick;
            private TextView description;

            MultiViewHolder(@NonNull View itemView){
                super(itemView);
                title = itemView.findViewById(R.id.text_view_title);
                tick = itemView.findViewById(R.id.tick_view);
                description = itemView.findViewById(R.id.text_view_description);
            }

            void bind(final Task task){
                tick.setVisibility(task.isChecked() ? View.VISIBLE : View.GONE);
                title.setText(task.getTitle());
                description.setText(task.getDescription());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        task.setChecked(!task.isChecked());
                        tick.setVisibility(task.isChecked() ? View.VISIBLE : View.GONE);
                    }
                });
            }
        }

        public List<Task> getAll() {
            return tasks;
        }

        public List<Task> getSelected() {
            List<Task> selected = new ArrayList<>();
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).isChecked()) {
                    selected.add(tasks.get(i));
                }
            }
            return selected;
        }

        public void removeFromList(Task removed){

            int position = findItemPosition(removed);
            if(position != -1) {
                tasks.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, tasks.size());
            }
        }

        private int findItemPosition(Task removed){
            int result = 0;
            for(Task item : tasks){
                if (removed.equals(item))
                    return result;
                result++;
            }
            return -1;
        }
}
