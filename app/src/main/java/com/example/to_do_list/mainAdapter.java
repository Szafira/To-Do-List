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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class mainAdapter extends RecyclerView.Adapter<mainAdapter.ViewHolder> {

    //Przywołanie zmiennych list i innych potrzebnych rzeczy

    private List<defineTasks> tasksList;
    private Activity context;
    private mainDatabase database;

    //konstruktor \(^.^)/
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

        holder.textView.setText(tasks.title);
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
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

                EditText editText = dialog.findViewById(R.id.editText);
                Button btUpdate = dialog.findViewById(R.id.Update);

                    // Nie wyłapuje tego co ma ale nie chce mi się teraz myśleć
                editText.setText(tasks.title);

                btUpdate.setOnClickListener(new View.OnClickListener()
                {
                @Override
                public void onClick(View v)
                    {
                        dialog.dismiss();

                        String uText = editText.getText().toString().trim();

                        database.mainDao().update(tID,uText);

                        tasksList.clear();
                        tasksList.addAll(database.mainDao().getAll());

                        notifyDataSetChanged();


                    }
                    });
             }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener()
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
        TextView textView;
        ImageView btEdit, btDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView =itemView.findViewById(R.id.textView);
            btEdit =itemView.findViewById(R.id.bt_edit);
            btDelete =itemView.findViewById(R.id.bt_delete);
        }
    }
}
