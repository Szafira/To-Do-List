package com.example.to_do_list;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Collection;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

//Zapytania do bazy danych
@Dao
public interface mainDao {
    @Insert(onConflict = REPLACE)
    void insert(defineTasks defineTasks);

    @Delete
    void delete(defineTasks defineTasks);

    @Delete
    void reset(List<defineTasks> defineTasks);
    //Update taska
    @Query("UPDATE tasksTable SET title = :sTitle, description = :sDescription, tags = :sTag, data = :sData WHERE tid = :tID")
    void update(int tID, String sTitle, String sDescription, String sTag, String sData);

    //Select do listy
    @Query("SELECT * FROM tasksTable")
    List<defineTasks> getAll();

    //@Query("SELECT tags FROM tasksTable")
    //List<defineTasks> getTags();


}
