package com.example.quizapp.database;

import android.app.Application;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.quizapp.data.State;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class stateRepository {

    private static stateRepository REPOSITORY = null;

    private StateDao mStateDao;
    private int Page_size = 15;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    private stateRepository(Application application){
        stateRoomDb db = stateRoomDb.getInstance(application);
        mStateDao = db.stateDao();
    }

    public static stateRepository getStateRepository(Application application){
        if(REPOSITORY==null){
            synchronized (stateRepository.class){
                if (REPOSITORY==null){
                    REPOSITORY = new stateRepository(application);
                }
            }
        }
        return REPOSITORY;
    }

    public void insertState(final State state){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.insertstate(state);
            }

        });
    }

    public void deleteState(final State state){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.deleteState(state);
            }
        });
    }

    public void updateState(final State state){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mStateDao.updateState(state);
            }
        });
    }

    public LiveData<PagedList<State>> getAllStates(String sortBy){
        LiveData data = new LivePagedListBuilder<>(
                mStateDao.getAllSortedStates(constructQuery(sortBy)),
                Page_size
        ).build();
        return data;
    }

//    public Future<List<State>> getQuizStates(){
//        Callable<List<State>> callable = new Callable<List<State>>() {
//            @Override
//            public List<State> call() throws Exception {
//                return mStateDao.getQuizState();
//            }
//        };
//        return executor.submit(callable);
//    }

    @WorkerThread
    public State getRandomState(){
        return mStateDao.getRandomState();
    }

    private SupportSQLiteQuery constructQuery(String sortBy){
        String query = "SELECT * FROM State ORDER BY " + sortBy + " ASC";
        return new SimpleSQLiteQuery(query);
    }


    public LiveData<List<State>> getQuizState(int value){
        return mStateDao.getQuizState(constructOptionsQuery(value));
    }

    private SupportSQLiteQuery constructOptionsQuery(int value){
        String q = "SELECT DISTINCT * FROM State ORDER BY RANDOM() LIMIT "+value;
        return new SimpleSQLiteQuery(q);
    }

}
