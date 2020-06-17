package com.example.quizapp.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.quizapp.data.State;

import java.util.List;

@Dao
public interface StateDao {

    @Query("SELECT * FROM State")
    DataSource.Factory<Integer, State> loadAllState();

    @RawQuery(observedEntities = State.class)
    DataSource.Factory<Integer, State> getAllSortedStates(SupportSQLiteQuery query);

    @Insert
    void insertstate(State state);

    @Delete
    void deleteState(State state);

    @Update
    void updateState(State state);

    @Query("SELECT DISTINCT * FROM state ORDER BY RANDOM() LIMIT 4")
    List<State> getQuizState();

    @Query("SELECT * FROM State ORDER BY RANDOM() LIMIT 1")
    State getRandomState();
}
