package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Zmienne
    EditText editText;
    Button taskAdd,taskReset;
    RecyclerView recyclerView;

    List<defineTasks> tasksList = new ArrayList<>();
    LinearLayoutManager linearLayout;
    mainDatabase database;
    mainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextTitle =findViewById(R.id.editTextTitle);
        EditText editTextDescription =findViewById(R.id.editTextDescription);
        EditText editTextTags =findViewById(R.id.editTextTags);
        EditText editTextData =findViewById(R.id.editTextData);

        taskAdd =findViewById(R.id.taskAdd);
        taskReset=findViewById(R.id.taskReset);
        recyclerView=findViewById(R.id.recycler_view);

        database = mainDatabase.getInstance(this);

        tasksList =database.mainDao().getAll();
        linearLayout = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayout);

        adapter = new mainAdapter(MainActivity.this,tasksList);

        recyclerView.setAdapter(adapter);
        //onclick dla dodania
        taskAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String tags = editTextTags.getText().toString().trim();
                String data = editTextData.getText().toString().trim();

                    if(!title.equals("")){
                        defineTasks tasks = new defineTasks();
                        tasks.setTitle(title);
                        if(description.equals("")) { tasks.setDescription(""); }
                        else { tasks.setDescription(description); }

                        if(tags.equals("")) { tasks.setTags(""); }
                        else { tasks.setTags(tags); }

                        if(data.equals("")) { tasks.setData(""); }
                        else { tasks.setData(data); }

                            database.mainDao().insert(tasks);
                            editTextTitle.setText("");
                            editTextDescription.setText("");
                            editTextTags.setText("");
                            editTextData.setText("");

                            tasksList.clear();
                            tasksList.addAll(database.mainDao().getAll());
                            adapter.notifyDataSetChanged();
                            
                        }



                }
            });

        //onclick dla resetu
        taskReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                database.mainDao().reset(tasksList);

                tasksList.clear();
                tasksList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}