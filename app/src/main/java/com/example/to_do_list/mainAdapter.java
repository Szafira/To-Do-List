package com.example.to_do_list;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.LabeledIntent;
import android.icu.text.CaseMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.ViewHolder> {

    //Przywołanie zmiennych list i innych potrzebnych rzeczy

    private List<defineTasks> tasksList;
    private Activity context;
    private mainDatabase database;

    //konstruktor
    public mainAdapter(Activity context,List<defineTasks> tasksList)
    {
        this.context = context;
        this.tasksList = tasksList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main,parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        defineTasks tasks = tasksList.get(position);

        database = mainDatabase.getInstance(context);

        holder.textViewTitle.setText(tasks.title);
        holder.textViewDescription.setText(tasks.description);
        holder.taskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                defineTasks data = tasksList.get(holder.getAdapterPosition());

                int tID = data.getTid();

                Dialog dialog = new Dialog(context);

                dialog.setContentView(R.layout.dialog_update);

                int width= WindowManager.LayoutParams.MATCH_PARENT;

                int height =WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                //EditText dla tytułu i opisu
                EditText editTextTitle = dialog.findViewById(R.id.editTextTitle);
                EditText editTextDescription = dialog.findViewById(R.id.editTextDescription);
                EditText editTextTags =dialog.findViewById(R.id.editTextTags);
                EditText editTextData =dialog.findViewById(R.id.editTextData);
                Button taskUpdate = dialog.findViewById(R.id.Update);

                //Ustawi tekst w okienku update
                editTextTitle.setText(tasks.title);
                editTextDescription.setText(tasks.description);
                editTextTags.setText(tasks.tags);
                editTextData.setText(tasks.data);

                //Przycisk Update
                taskUpdate.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                        dialog.dismiss();

                        String titleText = editTextTitle.getText().toString().trim();
                        String descriptionText = editTextDescription.getText().toString().trim();
                        String tagsText = editTextTags.getText().toString().trim();
                        String dataText = editTextData.getText().toString().trim();
                        database.mainDao().update(tID, titleText, descriptionText, tagsText, dataText);

                        tasksList.clear();
                        tasksList.addAll(database.mainDao().getAll());

                        notifyDataSetChanged();


                    }
                    });
             }
        });

        holder.taskDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
                    public void onClick(View v) {
                        defineTasks d = tasksList.get(holder.getAdapterPosition());

                        database.mainDao().delete(d);

                        int position = holder.getAdapterPosition();
                        tasksList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,tasksList.size());


        }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView taskEdit, taskDelete;
        TextView textViewTitle, textViewDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle =itemView.findViewById(R.id.textViewTitle);
            textViewDescription =itemView.findViewById(R.id.textViewDescription);
            taskEdit =itemView.findViewById(R.id.taskEdit);
            taskDelete =itemView.findViewById(R.id.taskDelete);
        }
    }
}
