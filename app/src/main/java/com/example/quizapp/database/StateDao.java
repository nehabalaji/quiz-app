package com.example.quizapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quizapp.data.State;

import java.util.List;

@Dao
public interface StateDao {

    @Query("SELECT * FROM State")
    LiveData<List<State>> loadAllState();

    @Insert
    void insertstate(State state);

    @Delete
    void deleteState(State state);

    @Update
    void updateState(State state);
}
