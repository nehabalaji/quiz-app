package com.example.quizapp.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "State")
public class State {

    @PrimaryKey(autoGenerate = true)
    private long stateID;
    @NonNull
    @ColumnInfo(name = "State")
    private String stateName;
    @NonNull
    @ColumnInfo(name = "Capital")
    private String capitalName;

    public State(){

    }

    public State(long stateID, @NonNull String mStateName, @NonNull String mCapitalName){
        this.stateName = mStateName;
        this.capitalName = mCapitalName;
        this.stateID = stateID;
    }

    @Ignore
    public State(@NonNull String mStateName, @NonNull String mCapitalName){

        this.stateName = mStateName;
        this.capitalName = mCapitalName;
    }

    public long getStateID() {
        return stateID;
    }

    public void setStateID(long stateID) {
        this.stateID = stateID;
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
