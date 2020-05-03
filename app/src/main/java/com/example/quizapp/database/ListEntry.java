package com.example.quizapp.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "States_and_capitals")
public class ListEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "State")
    private String stateName;
    @ColumnInfo(name = "Capital")
    private String capitalName;


    public ListEntry(@NonNull String mStateName, @NonNull String mCapitalName){
        this.stateName = mStateName;
        this.capitalName = mCapitalName;
    }

    @Ignore
    public ListEntry(@NonNull int mId,@NonNull String mStateName,@NonNull String mCapitalName){
        this.id = mId;
        this.stateName = mStateName;
        this.capitalName = mCapitalName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }
}
