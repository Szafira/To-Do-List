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

        editText =findViewById(R.id.editText);
        taskAdd =findViewById(R.id.taskAdd);
        taskReset=findViewById(R.id.taskReset);
        recyclerView=findViewById(R.id.recycler_view);

        database = mainDatabase.getInstance(this);

        tasksList =database.mainDao().getAll();
        linearLayout = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayout);

        adapter = new mainAdapter(MainActivity.this,tasksList);

        recyclerView.setAdapter(adapter);

        taskAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String title = editText.getText().toString().trim();
                    if(!title.equals("")){
                        defineTasks tasks = new defineTasks();
                        tasks.setTitle(title);
                        database.mainDao().insert(tasks);
                        editText.setText("");
                        tasksList.clear();
                        tasksList.addAll(database.mainDao().getAll());
                        adapter.notifyDataSetChanged();
                    }
            }
        });


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