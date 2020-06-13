package com.example.quizapp;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.quizapp.data.State;
import com.example.quizapp.database.stateRepository;

public class NotificationWorker extends Worker {

    private stateRepository mStateRepository;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mStateRepository = stateRepository.getStateRepository((Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        State state = mStateRepository.getRandomState();
        Notifications.getDailyNotifications(getApplicationContext(), state);
        return Result.success();
    }
}
