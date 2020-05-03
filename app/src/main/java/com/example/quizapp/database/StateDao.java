package com.example.quizapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StateDao {

    @Query("SELECT * FROM States_and_capitals")
    LiveData<List<ListEntry>> loadAllState();

    @Insert
    void insertstate(ListEntry listEntry);

    @Delete
    void deleteState(ListEntry listEntry);

    @Update
    void updateState(ListEntry listEntry);
}
