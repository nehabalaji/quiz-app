package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizapp.data.State;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        StateViewModel stateViewModel = new ViewModelProvider(this).get(StateViewModel.class);
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

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, addStateActivity.class);
                startActivity(intent);
            }
        });
    }
}
