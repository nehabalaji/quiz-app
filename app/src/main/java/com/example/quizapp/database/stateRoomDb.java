package com.example.quizapp.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.quizapp.data.State;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {State.class}, version = 1, exportSchema = false)
public abstract class stateRoomDb extends RoomDatabase {

    public abstract StateDao stateDao();
    private static stateRoomDb INSTANCE;
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static stateRoomDb getInstance(Context context){
        if(INSTANCE == null ){
            synchronized (stateRoomDb.class){
                INSTANCE = Room.
                        databaseBuilder(context.getApplicationContext(), stateRoomDb.class, "State")
                        .fallbackToDestructiveMigration()
                        .build();

            }
        }
        return INSTANCE;
    }
}
