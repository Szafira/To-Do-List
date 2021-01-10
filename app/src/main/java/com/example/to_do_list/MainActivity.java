package com.example.to_do_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Zmienne
    EditText editText;
    Button taskAdd, taskReset;
    RecyclerView recyclerView;

    List<defineTasks> tasksList = new ArrayList<>();
    LinearLayoutManager linearLayout;
    mainDatabase database;
    mainAdapter adapter;
    Fragment selectedFragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {


                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.nav_settings:
                        selectedFragment = new SettingsFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();

                return true;

            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        EditText editTextTags = findViewById(R.id.editTextTags);
        EditText editTextData = findViewById(R.id.editTextData);

        taskAdd = findViewById(R.id.taskAdd);
        View homeClick = findViewById(R.id.nav_home);
        View settingsClick = findViewById(R.id.nav_settings);
        recyclerView = findViewById(R.id.recycler_view);

        database = mainDatabase.getInstance(this);

        tasksList = database.mainDao().getAll();
        linearLayout = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayout);

        adapter = new mainAdapter(MainActivity.this, tasksList);

        recyclerView.setAdapter(adapter);
        //onclick dla dodania
        taskAdd.setOnClickListener(v -> {
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            String tags = editTextTags.getText().toString().trim();
            String data = editTextData.getText().toString().trim();

            if (!title.equals("")) {
                defineTasks tasks = new defineTasks();
                tasks.setTitle(title);
                if (description.equals("")) {
                    tasks.setDescription("");
                } else {
                    tasks.setDescription(description);
                }

                if (tags.equals("")) {
                    tasks.setTags("");
                } else {
                    tasks.setTags(tags);
                }

                if (data.equals("")) {
                    tasks.setData("");
                } else {
                    tasks.setData(data);
                }

                database.mainDao().insert(tasks);
                editTextTitle.setText("");
                editTextDescription.setText("");
                editTextTags.setText("");
                editTextData.setText("");

                tasksList.clear();
                tasksList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();

            }


        });
        homeClick.setOnClickListener(v -> setContentView(R.layout.activity_main));

        settingsClick.setOnClickListener(v -> {
            setContentView(R.layout.fragment_settings);
            Switch switch1;
            switch1 = findViewById(R.id.switch1);
            switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switch1.setChecked(true);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switch1.setChecked(false);
                }
            });


        });
        //bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.my_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) navListener);
    }
}
