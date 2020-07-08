package com.example.quizapp.custom;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.quizapp.data.State;
import com.example.quizapp.database.stateRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuizViewModel extends AndroidViewModel {

    private stateRepository mStateRepository;
    public MutableLiveData<List<State>> quizData = new MutableLiveData<>();
    public MutableLiveData<Integer> countValue = new MutableLiveData<>();
    public MutableLiveData<Integer> count = new MutableLiveData<>();
    public MutableLiveData<Integer> increment = new MutableLiveData<>();
    public LiveData<List<State>> state;
    int i=0;
    CustomLiveData trigger;
    int Count = 0 ;

    public QuizViewModel(@NonNull Application application) {
        super(application);
        mStateRepository = stateRepository.getStateRepository(application);
        count.setValue(4);
        increment.setValue(i);
        trigger = new CustomLiveData(count, increment);
        loadGame();
    }

    private void loadGame(){
//        if(Count<=4){
//            try {
//                quizData.postValue(mStateRepository.getQuizStates().get());
//                Count++;
//                countValue.setValue(Count);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
        state = Transformations.switchMap(trigger, new Function<Pair<Integer, Integer>, LiveData<List<State>>>() {
            @Override
            public LiveData<List<State>> apply(Pair<Integer, Integer> input) {
                return mStateRepository.getQuizState(input.first);
            }
        });
    }

    public void refreshGame(){
        i++;
        increment.postValue(i);
        loadGame();
    }
}
