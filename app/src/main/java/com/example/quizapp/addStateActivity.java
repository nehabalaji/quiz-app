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
    private StateViewModel stateViewModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_state);

        stateViewModel = new ViewModelProvider(this).get(StateViewModel.class);


        final EditText state = findViewById(R.id.stateET);
        final EditText capital = findViewById(R.id.capitalET);
        Button add = findViewById(R.id.addButton);

        final Bundle extras = getIntent().getExtras();

        if(extras!=null){
            String stateName = extras.getString(EXTRA_STATE_NAME, "");
            if(!stateName.isEmpty()){
                state.setText(stateName);
            }

            String capitalName = extras.getString(EXTRA_CAPITAL_NAME, "");
            if(!capitalName.isEmpty()){
                capital.setText(capitalName);
                capital.setSelection(capitalName.length());
                capital.requestFocus();
            }
            add.setText(R.string.save);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateName = state.getText().toString();
                String capitalName = capital.getText().toString();
                if(extras!=null && extras.containsKey(EXTRA_ID)){
                    long stateId = extras.getLong(EXTRA_ID, -1);
                    State state1 = new State(stateId, stateName, capitalName);
                    stateViewModel.Update(state1);
                }
                else{
                    State state1 = new State(stateName, capitalName);
                    stateViewModel.Update(state1);
                }

                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
