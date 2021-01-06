package com.example.to_do_list;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {defineTasks.class},version=1,exportSchema =false)
public abstract class mainDatabase extends RoomDatabase {

    private static mainDatabase database;

    private static String DATABASE_NAME ="database";

    public synchronized static mainDatabase getInstance(Context context)
    {
        if(database==null) {
            database = Room.databaseBuilder(context.getApplicationContext()
                    ,mainDatabase.class, "database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
        }

        public abstract mainDao mainDao();

}
