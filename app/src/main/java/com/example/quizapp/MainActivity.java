package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizapp.custom.QuizViewModel;
import com.example.quizapp.custom.QuizViewModelFactory;
import com.example.quizapp.custom.quizView;
import com.example.quizapp.data.State;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private QuizViewModel quizViewModel;
    private quizView mQuizView;
    private String noOfOptions;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        noOfOptions = preferences.getString("list_preference_2", "Four");
        final int value;
        if(noOfOptions.equals("four")){
            value = 4;
        }
        else{
            value=3;
        }

        quizViewModel = (new ViewModelProvider(this,new  QuizViewModelFactory(this.getApplication()))).get(QuizViewModel.class);
        mQuizView = findViewById(R.id.quiz_view);

        quizViewModel.state.observe(this, new Observer<List<State>>() {
            @Override
            public void onChanged(List<State> states) {
               if(states!=null){
                   if(states.size()== 4 || states.size()==3){
                       mQuizView.setData(states, value);
                       Log.v("TAG",""+states.toString());
                   }
                   else{
                       Toast.makeText(MainActivity.this, "Add more states", Toast.LENGTH_LONG).show();
                   }
               }
            }
        });

//        quizViewModel.countValue.observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                if(integer>4){
//                    mQuizView.GameResult();
//                    Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
//                    Log.v("TAG1", mQuizView.toString());
//                }
//            }
//        });

        mQuizView.setOptionsClickedListener(new quizView.OptionsClickListener() {
            @Override
            public void optionClicked(Boolean result) {
                UpdateResult(result);
            }
        });
    }

    private void UpdateResult(Boolean result){
        if(result){
            Toast.makeText(MainActivity.this, "Correct Answer", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Wrong Answer", Toast.LENGTH_LONG).show();
        }
        quizViewModel.refreshGame();
        mQuizView.reset();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.list: Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                return true;

            case R.id.settings: Intent intent1 = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent1);
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("list_preference_2")){
                String c = preferences.getString(key, "four");
                final int val;
                if(c.equals("four")){
                    val=4;
                }
                else{
                    val = 3;
                }
                quizViewModel.count.postValue(val);
                quizViewModel.refreshGame();
                mQuizView.reset();
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        preferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!noOfOptions.equals(preferences.getString("list_preference_2", "Four"))){
            quizViewModel.refreshGame();
            mQuizView.reset();
            recreate();
        }
    }
}
