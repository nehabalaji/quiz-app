package com.example.quizapp;

import androidx.appcompat.app.AppCompatDelegate;

public class NightMode {
    public enum NightModeEnum{
        AUTO(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY),
        ON(AppCompatDelegate.MODE_NIGHT_YES),
        OFF(AppCompatDelegate.MODE_NIGHT_NO);

        public int value;

        NightModeEnum(int modeNightNo){
            value = modeNightNo;
        }
    }
}
