package com.example.quizapp.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.quizapp.R;
import com.example.quizapp.data.State;

import java.util.List;
import java.util.Random;

public class quizView extends LinearLayout {

    private State correctState;
    private RadioGroup optionsRadio;
    private int correctOptionId;
    private OptionsClickListener optionsClickListener;

    public quizView(Context context) {
        super(context);
        initRadios();
    }

    public quizView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRadios();
    }

    void initRadios(){
        optionsRadio = new RadioGroup(getContext());
        optionsRadio.setId(View.generateViewId());
    }

    public interface OptionsClickListener{
        void optionClicked(Boolean result);
    }

    public void setOptionsClickedListener(OptionsClickListener optionsClickedListener){
        this.optionsClickListener = optionsClickedListener;
    }

    public void setData(List<State> states, int value){
        Random random = new Random(System.currentTimeMillis());
        int correctOption = random.nextInt(value);

        correctState = states.get(correctOption);

        TextView questionTv = new TextView(getContext());
        String question = "What is the capital of " + correctState.getStateName();
        questionTv.setText(question);

        this.addView(questionTv);

        this.addView(optionsRadio);

        RadioButton[] radios = new RadioButton[value];
        radios[correctOption] = new RadioButton(getContext());
        radios[correctOption].setId(View.generateViewId());
        radios[correctOption].setText(correctState.getCapitalName());

        correctOptionId = radios[correctOption].getId();

        for(int i=0, j=0; i<value; i++, j++) {
            if (i == correctOption) {
                optionsRadio.addView(radios[correctOption]);
                continue;
            } else {
                radios[i] = new RadioButton(getContext());
                radios[i].setId(View.generateViewId());
                radios[i].setText(states.get(j).getCapitalName());
                optionsRadio.addView(radios[i]);
            }
            initListeners();
        }
    }

    private void initListeners(){
        optionsRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(optionsClickListener!=null){
                    if(checkedId==correctOptionId){
                        optionsClickListener.optionClicked(true);
                    }
                    else{
                        optionsClickListener.optionClicked(false);
                    }
                }
            }
        });
    }

//    public void GameResult(){
//        reset();
//        TextView result = new TextView(getContext());
//        String r = "You have answered";
//        result.setText(r);
//        result.setTextColor(getResources().getColor(R.color.red));
//        this.addView(result);
//        Log.v("TAG", result.toString());
//    }

    public void reset(){
        optionsRadio.removeAllViews();
        this.removeAllViews();
    }
}
