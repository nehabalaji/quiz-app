package com.example.quizapp.database;


import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.quizapp.data.State;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {State.class}, version = 1, exportSchema = false)
public abstract class stateRoomDb extends RoomDatabase {

    public abstract StateDao stateDao();
    private static stateRoomDb INSTANCE;
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static stateRoomDb getInstance(final Context context){
        if(INSTANCE == null ){
            synchronized (stateRoomDb.class){
                INSTANCE = Room.
                        databaseBuilder(context.getApplicationContext(), stateRoomDb.class, "State")
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        prePopulate(context.getAssets(), INSTANCE.stateDao());
                                    }
                                });
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build();

            }
        }
        return INSTANCE;
    }

    public static void prePopulate(AssetManager assetManager, StateDao stateDao){
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String json = "";
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open("state-capital.json")));
            String mLine;
            while ((mLine=bufferedReader.readLine()) != null){
                stringBuilder.append(mLine);
            }
            json = stringBuilder.toString();
        } catch (IOException e) {
            //Log
        }
        finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //Log
                }
            }
        }
        try {
            JSONObject states = new JSONObject(json);
            JSONObject section = states.getJSONObject("sections");
            populateFromJson(section.getJSONArray("States (A-L)"), stateDao);
            populateFromJson(section.getJSONArray("States (A-L)"), stateDao);
            populateFromJson(section.getJSONArray("Union Territories"), stateDao);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void populateFromJson(JSONArray states, StateDao stateDao){
        try {
            for(int i=0; i<states.length(); i++){
                JSONObject stateData = states.getJSONObject(i);
                String stateName = stateData.getString("key");
                String capitalName = stateData.getString("val");
                stateDao.insertstate(new State(stateName, capitalName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
