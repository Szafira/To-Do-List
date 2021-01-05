package com.example.to_do_list;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class defineTask<data> {

    @Entity(tableName="tasksTable")
    public class Task
    {
        @PrimaryKey (autoGenerate = true)
        public int tid; //Task id

        @ColumnInfo(name = "title")
        public String title;

        @ColumnInfo(name = "description")
        public String description;

        @ColumnInfo(name = "date")
        public int date;

    }


//Brakuje definicji dla ikonki i tagów.
}
