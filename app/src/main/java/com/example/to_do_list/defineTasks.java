package com.example.to_do_list;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName="tasksTable")
public class defineTasks implements Serializable {


    @PrimaryKey(autoGenerate = true)
    public int tid; //Task id

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "tags")
    public String tags;

    @ColumnInfo(name = "data")
    public String data;

    //Gettery i settery
    //ID
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    //Title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //Description

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    //Tagi

    public String getTags() { return tags; }

    public void setTags(String tags) { this.tags = tags; }

    //Data

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }
}

