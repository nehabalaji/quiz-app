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

    private ClickListener clickListener;

    @NonNull
    @Override
    public stateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View ItemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new stateViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull stateViewHolder holder, final int position) {
        final State currentItem = getItem(position);
        if(currentItem!=null){
            holder.bind(currentItem);

            if (clickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(v, position);
                    }
                });
            }
        }
    }

    public  void setOnItemClockListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(View v, int position);
    }

    public State getStateAtPosition(int position){
        return getItem(position);
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

