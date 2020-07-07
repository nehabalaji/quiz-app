package com.example.quizapp.custom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.quizapp.data.State;
import com.example.quizapp.database.stateRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuizViewModel extends AndroidViewModel {

    private stateRepository mStateRepository;
    public MutableLiveData<List<State>> quizData = new MutableLiveData<>();
    public MutableLiveData<Integer> countValue = new MutableLiveData<>();
    int count = 0 ;

    public QuizViewModel(@NonNull Application application) {
        super(application);
        mStateRepository = stateRepository.getStateRepository(application);
        loadGame();
    }

    private void loadGame(){
        if(count<=4){
            try {
                quizData.postValue(mStateRepository.getQuizStates().get());
                count++;
                countValue.setValue(count);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    public void refreshGame(){
        loadGame();
    }
}
