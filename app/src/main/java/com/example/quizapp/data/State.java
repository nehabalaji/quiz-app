package com.example.quizapp.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "State")
public class State {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    @ColumnInfo(name = "State")
    private String stateName;
    @NonNull
    @ColumnInfo(name = "Capital")
    private String capitalName;

    public State(){

    }

    public State(long stateId, @NonNull String mStateName, @NonNull String mCapitalName){
        this.stateName = mStateName;
        this.capitalName = mCapitalName;
        this.id = stateId;
    }

    @Ignore
    public State(@NonNull String mStateName, @NonNull String mCapitalName){

        this.stateName = mStateName;
        this.capitalName = mCapitalName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean equals(State state){
        return (stateName==state.getStateName()) && (capitalName==state.getCapitalName());
    }
}
