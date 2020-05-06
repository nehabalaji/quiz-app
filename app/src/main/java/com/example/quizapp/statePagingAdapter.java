package com.example.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.quizapp.data.State;

public class statePagingAdapter extends PagedListAdapter<State, stateViewHolder> {
    protected statePagingAdapter() {
        super(itemCallback);
    }

    @NonNull
    @Override
    public stateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View ItemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new stateViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull stateViewHolder holder, int position) {
        final State currentItem = getItem(position);
        if(currentItem!=null){
            holder.bind(currentItem);
        }
    }

    private static DiffUtil.ItemCallback<State> itemCallback = new DiffUtil.ItemCallback<State>() {
        @Override
        public boolean areItemsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return oldItem.getStateName()==newItem.getStateName();
        }

        @Override
        public boolean areContentsTheSame(@NonNull State oldItem, @NonNull State newItem) {
            return oldItem.equals(newItem);
        }
    };
}
