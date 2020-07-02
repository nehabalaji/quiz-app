package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.quizapp.data.State;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class ListActivity extends AppCompatActivity {

    public static final String EXTRA_STATE_NAME = "extra_state_name_to_be_updated";
    public static final String EXTRA_CAPITAL_NAME = "extra_state_capital_to_be_updated";
    public static final String EXTRA_ID = "extra_state_id";
    public static final int UPDATE_STATE_REQUEST_CODE = 1;
    public static final int NEW_STATE_REQUEST_CODE = 2;
    private StateViewModel stateViewModel;
    private State currentState;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        stateViewModel = new ViewModelProvider(this).get(StateViewModel.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sort = sharedPreferences.getString("list_preference_1", "stateID");
        stateViewModel.changeSortingOrder(sort);

        RecyclerView recyclerView = findViewById(R.id.stateAndCapital);
        final statePagingAdapter mStatePagingAdapter = new statePagingAdapter();
        recyclerView.setAdapter(mStatePagingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stateViewModel.pagedListLiveData.observe(this, new Observer<PagedList<State>>() {
            @Override
            public void onChanged(PagedList<State> states) {
                mStatePagingAdapter.submitList(states);
            }
        });

        mStatePagingAdapter.setOnItemClockListener(new statePagingAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                State currentState = mStatePagingAdapter.getStateAtPosition(position);
                launchUpdateStateACtivity(currentState);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, addStateActivity.class);
                startActivityForResult(intent, NEW_STATE_REQUEST_CODE);
            }
        });

        final ConstraintLayout constraintLayout = findViewById(R.id.list);

        final Snackbar snackbar = Snackbar.make(constraintLayout, "State is deleted", BaseTransientBottomBar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stateViewModel.Insert(currentState);
                    }
                });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                currentState = mStatePagingAdapter.getStateAtPosition(position);
                stateViewModel.Delete(currentState);
                snackbar.show();
            }


        });

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    private void launchUpdateStateACtivity(State currentState) {
        Intent intent = new Intent(ListActivity.this, addStateActivity.class);
        intent.putExtra(EXTRA_STATE_NAME, currentState.getStateName());
        intent.putExtra(EXTRA_CAPITAL_NAME, currentState.getCapitalName());
        intent.putExtra(EXTRA_ID, currentState.getStateID());
        startActivityForResult(intent, UPDATE_STATE_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("list_preference_1")){
                String s = sharedPreferences.getString(key, "stateID");
                stateViewModel.changeSortingOrder(s);
            }
        }
    };


}
