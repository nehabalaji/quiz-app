package com.example.quizapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.quizapp.data.State;
import com.example.quizapp.database.stateRepository;

import java.util.List;

public class StateViewModel extends AndroidViewModel {

   public stateRepository mStateRepository;
   public LiveData<PagedList<State>> pagedListLiveData;

    public StateViewModel(@NonNull Application application) {
        super(application);
        mStateRepository = stateRepository.getStateRepository(application);
        pagedListLiveData =  mStateRepository.getAllStates();
    }

    public void Insert(State state){
        mStateRepository.insertState(state);
    }

    public void Delete(State state){
        mStateRepository.deleteState(state);
    }

    public void Update(State state){
        mStateRepository.updateState(state);
    }


}
