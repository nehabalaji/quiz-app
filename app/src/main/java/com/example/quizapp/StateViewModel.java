package com.example.quizapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.example.quizapp.data.State;
import com.example.quizapp.database.stateRepository;

import java.util.List;

public class StateViewModel extends AndroidViewModel {

   public stateRepository mStateRepository;
   public LiveData<PagedList<State>> pagedListLiveData;
   int count =0;

   private MutableLiveData<String> sortOrderChanged = new MutableLiveData<>();

    public StateViewModel(@NonNull Application application) {
        super(application);
        mStateRepository = stateRepository.getStateRepository(application);
        sortOrderChanged.setValue("stateID");
        pagedListLiveData = Transformations.switchMap(sortOrderChanged, new Function<String, LiveData<PagedList<State>>>() {
            @Override
            public LiveData<PagedList<State>> apply(String input) {
                return mStateRepository.getAllStates(input);
            }
        });
    }

    public void changeSortingOrder(String sortBy){
        switch (sortBy){
            case "Name" : sortBy = "State";
            break;

            case "Capital" : sortBy = "Capital";
            break;

            case "ID" : sortBy = "stateID";
            break;
        }
        sortOrderChanged.postValue(sortBy);
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
