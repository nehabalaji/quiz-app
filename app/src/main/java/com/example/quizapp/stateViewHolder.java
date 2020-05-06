package com.example.quizapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.data.State;

public class stateViewHolder extends RecyclerView.ViewHolder {

    TextView stateTv, capitalTv;
    public stateViewHolder(@NonNull View itemView) {
        super(itemView);
        stateTv = itemView.findViewById(R.id.state);
        capitalTv = itemView.findViewById(R.id.cap);
    }

    public void bind(State state){
        stateTv.setText(state.getStateName());
        capitalTv.setText(state.getCapitalName());
    }
}

