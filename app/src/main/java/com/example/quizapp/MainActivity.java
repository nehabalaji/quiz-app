package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizViewModel = (new ViewModelProvider(this,new  QuizViewModelFactory(this.getApplication()))).get(QuizViewModel.class);
        mQuizView = findViewById(R.id.quiz_view);

        quizViewModel.quizData.observe(this, new Observer<List<State>>() {
            @Override
            public void onChanged(List<State> states) {
               if(states!=null){
                   if(states.size() >= 4){
                       mQuizView.setData(states);
                   }
                   else{
                       Toast.makeText(MainActivity.this, "Add more states", Toast.LENGTH_LONG).show();
                   }
               }
            }
        });

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
        if(id==R.id.settings){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.list){
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
