package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizapp.data.State;

public class addStateActivity extends AppCompatActivity {

    public static final String EXTRA_STATE_NAME = "extra_state_name_to_be_updated";
    public static final String EXTRA_CAPITAL_NAME = "extra_state_capital_to_be_updated";
    public static final String EXTRA_ID = "extra_state_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_state);

        final StateViewModel stateViewModel = new ViewModelProvider(this).get(StateViewModel.class);


        final EditText state = findViewById(R.id.stateET);
        final EditText capital = findViewById(R.id.capitalET);
        Button add = findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateName = state.getText().toString();
                String capitalName = capital.getText().toString();
                if(!(stateName.isEmpty()) && !(capitalName.isEmpty())){
                    State state1 = new State(stateName, capitalName);
                    stateViewModel.Insert(state1);
                }
            }
        });

    }
}
