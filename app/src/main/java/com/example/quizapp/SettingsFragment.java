package com.example.quizapp;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment(){

    }

    private String NOTIFICATION_WORK = "work";
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        SwitchPreference switchPreference = findPreference("switch_preference_1");
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                long current = System.currentTimeMillis();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);

                if(calendar.getTimeInMillis() < current){
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }

                final WorkManager manager = WorkManager.getInstance(requireActivity());
                final PeriodicWorkRequest.Builder workRequestBuilder = new PeriodicWorkRequest.Builder(
                        NotificationWorker.class,
                        1,
                        TimeUnit.DAYS
                );

                workRequestBuilder.setInitialDelay(calendar.getTimeInMillis()-current, TimeUnit.MILLISECONDS);

                Boolean noti = (Boolean) newValue;

                if(noti){
                    manager.enqueueUniquePeriodicWork(NOTIFICATION_WORK, ExistingPeriodicWorkPolicy.REPLACE, workRequestBuilder.build());
                }else{
                    manager.cancelUniqueWork(NOTIFICATION_WORK);
                }
                return true;
            }
        });

        ListPreference darkmode = findPreference(getString(R.string.pref_key_night));
        darkmode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String theme = (String) newValue;
                NightMode.NightModeEnum value = NightMode.NightModeEnum.valueOf(theme.toUpperCase(Locale.US));
                updateTheme(value.value);
                return true;
            }
        });
    }

    private void updateTheme(int value) {
        AppCompatDelegate.setDefaultNightMode(value);
        requireActivity().recreate();
    }
}
