package com.example.to_do_list;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName="tasksTable")
public class defineTasks implements Serializable {


        @PrimaryKey (autoGenerate = true)
        public int tid; //Task id

        @ColumnInfo(name = "title")
        public String title;


    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    }


//Do dodania: opis, tagi, data

